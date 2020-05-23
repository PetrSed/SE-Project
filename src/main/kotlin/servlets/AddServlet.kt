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
        val departments = wrapper.getDepartments()
        val depatments_str = StringBuilder()
        for (department in departments) {
            depatments_str.append("${department.name},")
        }
        request.setAttribute("department", depatments_str)
        request.getRequestDispatcher("../addUser.jsp").forward(request, response)
    }

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        val wrapper = Wrapper()
        wrapper.connect()
        val fio = request.getParameter("fio")
        val departmentName = request.getParameter("country")
        val personalNumber = request.getParameter("personalNumber")
        val workNumber = request.getParameter("workNumber")
        val homeNumber = request.getParameter("homeNumber")
        println(departmentName)
        val departmentId = wrapper.getDepartmentIdByName(departmentName)
        println("$fio, $departmentId, $personalNumber, $workNumber, $homeNumber")
        response.writer.print("Success")
    }
}

@WebServlet(name = "AddDepartmentServlet", urlPatterns = ["add/department"])
class AddDepartmentServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        request.getRequestDispatcher("../addDepartment.jsp").forward(request, response)
    }

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        val wrapper = Wrapper()
        wrapper.connect()
        val name = request.getParameter("name")
        val phone = request.getParameter("phone")
        wrapper.addDepartment(Department(name, phone))
        response.writer.print("Success")
    }
}
