package servlets

import Wrapper
import com.google.gson.GsonBuilder
import domains.Department
import domains.User
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.net.URLEncoder.encode


@WebServlet(name = "ApiGetDepartmentsServlet", urlPatterns = ["api/departments"])
class ApiGetDepartmentsServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val wrapper = Wrapper()
        wrapper.connect()
        val departments = wrapper.getDepartments()
        val builder = GsonBuilder()
        val gson = builder.create()
        wrapper.close()
        response.setContentType("text/html;charset=UTF-8")
        response.setCharacterEncoding("UTF-8")
        response.writer.print(gson.toJson(departments))
    }
}
@WebServlet(name = "ApiGetDepartmentServlet", urlPatterns = ["api/department"])
class ApiGetDepartmentServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val wrapper = Wrapper()
        wrapper.connect()
        val id = request.getParameter("id")
        val name = request.getParameter("name")
        var departments: List<Department>? = mutableListOf()
        if (id != null) {
            departments = listOf(wrapper.getDepartment(id.toInt()))
        } else if (name != null){
            departments = wrapper.getDepartmentByName(name)
        }  else {
            departments = wrapper.getDepartments()
        }
        val builder = GsonBuilder()
        val gson = builder.create()
        wrapper.close()
        response.setContentType("text/html;charset=UTF-8")
        response.setCharacterEncoding("UTF-8")
        response.writer.print(gson.toJson(departments))
    }
    override fun doDelete(request: HttpServletRequest, response: HttpServletResponse) {
        val wrapper = Wrapper()
        wrapper.connect()
        val id = request.getParameter("id")
        wrapper.deleteDepartment(id.toInt())
        wrapper.close()
        response.writer.print("Success")
    }
}