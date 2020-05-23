import domains.User
import java.sql.DriverManager
import java.sql.Connection
import java.io.Closeable
import java.io.File
import org.flywaydb.core.Flyway

class Wrapper : Closeable {
    private var con: Connection? = null
    private val url = "jdbc:h2:./pb"
    private val login = "se"
    private val pass = ""
    fun dbExists(): Boolean = File("pb.h2.db").exists()
    fun connect() {
        if (!dbExists()) {
            initDatabase()
        }
        con = DriverManager.getConnection(url, login, pass)
    }

    override fun close() {
        con?.close()
    }

    private fun initDatabase() {
        val flyway = Flyway.configure().dataSource("$url;MV_STORE=FALSE", login, pass).locations("filesystem:db").load()
        flyway.migrate()
    }

    fun getUser(id: Int): User? {
        val getUser =
            con!!.prepareStatement("SELECT fio, department, personal_number, work_number, home_number FROM staff WHERE id = ?")
        getUser.setInt(1, id)
        val res = getUser.executeQuery()
        res.next()
        val fio = res.getString("fio")
        val departmentId = res.getInt("department")
        val personalNumber = res.getString("personal_number")
        val workNumber = res.getString("work_number")
        val homeNumber = res.getString("home_number")
        res.close()
        getUser.close()
        return User(fio, departmentId, personalNumber, workNumber, homeNumber)
    }
}