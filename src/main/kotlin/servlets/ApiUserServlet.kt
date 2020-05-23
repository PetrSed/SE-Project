package servlets

import Wrapper
import com.google.gson.GsonBuilder
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import domains.User


@WebServlet(name = "ApiGetUsersServlet", urlPatterns = ["api/users"])
class ApiGetUsersServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val wrapper = Wrapper()
        wrapper.connect()
        val users = wrapper.getUsers()
        val builder = GsonBuilder()
        val gson = builder.create()
        wrapper.close()
        response.setContentType("text/html;charset=UTF-8")
        response.setCharacterEncoding("UTF-8")
        response.writer.print(gson.toJson(users))

    }
}
@WebServlet(name = "ApiUserServlet", urlPatterns = ["api/user"])
class ApiUserServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val wrapper = Wrapper()
        wrapper.connect()
        val id = request.getParameter("id")
        val fio = request.getParameter("fio")
        val department = request.getParameter("department")
        var users: List<User>? = mutableListOf()
        if (id != null) {
            users = listOf(wrapper.getUserById(id.toInt()))
        } else if (fio != null){
            users = wrapper.getUserByFIO(fio)
        } else if (department != null){
            users = wrapper.getUserByDepartment(department.toInt())
        } else {
            users = wrapper.getUsers()
        }
        val builder = GsonBuilder()
        val gson = builder.create()
        wrapper.close()
        response.setContentType("text/html;charset=UTF-8")
        response.setCharacterEncoding("UTF-8")
        response.writer.print(gson.toJson(users))
    }
    override fun doDelete(request: HttpServletRequest, response: HttpServletResponse) {
        val wrapper = Wrapper()
        wrapper.connect()
        val id = request.getParameter("id")
        wrapper.deleteUser(id.toInt())
        wrapper.close()
        response.writer.print("Success")
    }
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        val wrapper = Wrapper()
        wrapper.connect()
        val fio = request.getParameter("fio")
        val departmentId = request.getParameter("country")
        val personalNumber = request.getParameter("personalNumber")
        val workNumber = request.getParameter("workNumber")
        val homeNumber = request.getParameter("homeNumber")
        wrapper.addUser(User(fio, departmentId.toInt(), personalNumber, workNumber, homeNumber))
        response.writer.print("Success")
    }
}

