package servlets

import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import Wrapper
import domains.Department
import domains.User


@WebServlet(name = "AddUserServlet", urlPatterns = ["add/user"])
class AddUserServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val wrapper = Wrapper()
        wrapper.connect()
        val query = request.queryString
        val fio = request.getParameter("fio")
        val department_id = request.getParameter("department_id")
        val personalNumber = request.getParameter("personalNumber")
        val workNumber = request.getParameter("workNumber")
        val homeNumber = request.getParameter("homeNumber")
        val res = wrapper.addUser(User(fio, department_id.toInt(), personalNumber, workNumber, homeNumber))
        response.writer.print("Success")
    }
}
@WebServlet(name = "AddDepartmentServlet", urlPatterns = ["add/department"])
class AddDepartmentServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val wrapper = Wrapper()
        wrapper.connect()
        val query = request.queryString
        val name = request.getParameter("name")
        val phone = request.getParameter("phone")
        val res = wrapper.addDepartment(Department(name, phone))
        response.writer.print("Success")
    }
}