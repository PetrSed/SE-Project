import domains.Department
import domains.User
import java.sql.DriverManager
import java.sql.Connection
import java.io.Closeable
import java.io.File
import org.flywaydb.core.Flyway
import java.sql.ResultSet

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

    fun getUser(id: Int): User? {
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

    fun getDepartment(id: Int): Department? {
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
}