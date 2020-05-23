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
}