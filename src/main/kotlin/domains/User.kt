package domains

data class User(
    val fio: String,
    val department: Int,
    val personal_number: String,
    val work_number: String,
    val home_number: String
)
