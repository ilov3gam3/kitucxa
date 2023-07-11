package Controller.User;

import Dao.UserDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

public class ServletViewStudentInfo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String student_code = request.getParameter("student_code");
        UserDao userDao = new UserDao();
        request.setAttribute("user", userDao.getUserByCode(student_code));
        request.getRequestDispatcher("/WEB-INF/views/user/student-info.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
