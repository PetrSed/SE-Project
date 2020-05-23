package servlets

import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import Wrapper


@WebServlet(name = "GetUserServlet", urlPatterns = ["get/user"])
class GetUserServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val wrapper = Wrapper()
        wrapper.connect()
        val query = request.queryString
        val id = query.substringAfter("id=")
        val user = wrapper.getUserById(id.toInt())
        val department = wrapper.getDepartment(user!!.department)
        request.setAttribute("id", id)
        request.setAttribute("fio", user.fio)
        request.setAttribute("departmentName", department!!.name)
        request.setAttribute("departmentPhone", department.phone)
        request.setAttribute("personalNumber", user.personalNumber)
        request.setAttribute("workNumber", user.workNumber)
        request.setAttribute("homeNumber", user.homeNumber)
        wrapper.close()
        request.getRequestDispatcher("../userResponse.jsp").forward(request, response)
    }
}
@WebServlet(name = "GetDepartmentServlet", urlPatterns = ["get/department"])
class GetDepartmentServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val wrapper = Wrapper()
        wrapper.connect()
        println("SUCCESS CONN")
        val query = request.queryString
        val id = query.substringAfter("id=")
        val department = wrapper.getDepartment(id.toInt())
        request.setAttribute("id", id)
        request.setAttribute("name", department!!.name)
        request.setAttribute("phone", department.phone)
        wrapper.close()
        request.getRequestDispatcher("../departmentResponse.jsp").forward(request, response)
    }
}

