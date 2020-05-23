import domains.Department
import domains.User
import org.flywaydb.core.Flyway
import java.io.Closeable
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.net.URLEncoder.encode

class Wrapper : Closeable {
    private var con: Connection? = null
    private val url = "jdbc:h2:./pb"
    private val login = "se"
    private val pass = ""
    fun dbExists(): Boolean = File("pb.h2.db").exists()
    fun connect() {
        initDatabase()
        con = DriverManager.getConnection(url, login, pass)
    }

    override fun close() {
        con?.close()
    }

    private fun initDatabase() {
        println("INIT")
        val flyway = Flyway.configure().dataSource("$url;MV_STORE=FALSE", login, pass).locations("filesystem:db").load()
        flyway.migrate()
    }

    fun getUserById(id: Int): User {
        val getUser =
            con!!.prepareStatement("SELECT fio, department, personalNumber, workNumber, homeNumber FROM staff WHERE id = ?")
        getUser.setInt(1, id)
        val res = getUser.executeQuery()
        res.next()
        val fio = res.getString("fio")
        val departmentId = res.getInt("department")
        val personalNumber = res.getString("personalNumber")
        val workNumber = res.getString("workNumber")
        val homeNumber = res.getString("homeNumber")
        res.close()
        getUser.close()
        return User(fio, departmentId, personalNumber, workNumber, homeNumber)
    }
    fun getUserByFIO(search: String): MutableList<User> {
        val req = "SELECT * FROM STAFF WHERE fio LIKE '%$search%'"
        val getUser = con!!.prepareStatement(req)
        val res = getUser.executeQuery()
        val users: MutableList<User> = mutableListOf()
        while (res.next()) {
            val fio = res.getString("fio")
            val department = res.getInt("department")
            val personalNumber = res.getString("personalNumber")
            val workNumber = res.getString("workNumber")
            val homeNumber = res.getString("homeNumber")
            users.add(User(fio, department, personalNumber, workNumber, homeNumber))
        }
        res.close()
        getUser.close()
        return users
    }
    fun getDepartmentByName(search: String): MutableList<Department> {
        val req = "SELECT * FROM DEPARTMENTS WHERE name LIKE '%$search%'"
        val getDepartment = con!!.prepareStatement(req)
        val res = getDepartment.executeQuery()
        val departments: MutableList<Department> = mutableListOf()
        while (res.next()) {
            val name = res.getString("name")
            val phone = res.getString("phone")
            departments.add(Department(name, phone))
        }
        res.close()
        getDepartment.close()
        return departments
    }
    fun getUserByDepartment(department_id: Int): MutableList<User> {
        val getUser =
            con!!.prepareStatement("SELECT * FROM STAFF WHERE department = ?")
        getUser.setInt(1, department_id)
        val res = getUser.executeQuery()
        val users: MutableList<User> = mutableListOf()
        while (res.next()) {
            val fio = res.getString("fio")
            val department = res.getInt("department")
            val personalNumber = res.getString("personalNumber")
            val workNumber = res.getString("workNumber")
            val homeNumber = res.getString("homeNumber")
            users.add(User(fio, department, personalNumber, workNumber, homeNumber))
        }
        res.close()
        getUser.close()
        return users
    }
    fun addUser(user: User): Int? {
        val addUser =
            con!!.prepareStatement("INSERT INTO staff(fio, department, workNumber, personalNumber, homeNumber) VALUES (?, ?, ?, ?, ?)")
        addUser.setString(1, user.fio)
        addUser.setInt(2, user.department)
        addUser.setString(3, user.workNumber)
        addUser.setString(4, user.personalNumber)
        addUser.setString(5, user.homeNumber)
        addUser.execute()
        addUser.close()
        return 200
    }

    fun getDepartment(id: Int): Department {
        val getDepartment = con!!.prepareStatement("SELECT name, phone FROM departments WHERE id = ?")
        getDepartment.setInt(1, id)
        val res = getDepartment.executeQuery()
        res.next()
        val name = res.getString("name")
        val phone = res.getString("phone")
        res.close()
        getDepartment.close()
        return Department(name, phone)
    }
    fun addDepartment(department: Department): Int? {
        val addDepartment =
            con!!.prepareStatement("INSERT INTO departments(name, phone) VALUES (?, ?)")
        addDepartment.setString(1, department.name)
        addDepartment.setString(2, department.phone)
        addDepartment.execute()
        addDepartment.close()
        return 200
    }
    fun test(){
        println("test")
        val getDepartment = con!!.prepareStatement("SELECT * FROM staff")
        val res = getDepartment.executeQuery()
        println(res)
    }
    fun getUsers(): List<User> {
        val getUsers = con!!.prepareStatement("SELECT * FROM STAFF")
        val res = getUsers.executeQuery()
        val users: MutableList<User> = mutableListOf()
        while (res.next()) {
            val fio = res.getString("fio")
            val department = res.getInt("department")
            val personalNumber = res.getString("personalNumber")
            val workNumber = res.getString("workNumber")
            val homeNumber = res.getString("homeNumber")
            users.add(User(fio, department, personalNumber, workNumber, homeNumber))
        }
        getUsers.close()
        return users
    }
    fun getDepartments(): List<Department> {
        val getDepartments = con!!.prepareStatement("SELECT * FROM DEPARTMENTS")
        val res = getDepartments.executeQuery()
        val departments: MutableList<Department> = mutableListOf()
        while (res.next()) {
            val name = res.getString("name")
            val phone = res.getString("phone")
            departments.add(Department(name, phone))
        }
        getDepartments.close()
        return departments
    }
}
