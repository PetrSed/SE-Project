package domains

data class User(
    val fio: String,
    val department: Int,
    val personalNumber: String,
    val workNumber: String,
    val homeNumber: String
)
