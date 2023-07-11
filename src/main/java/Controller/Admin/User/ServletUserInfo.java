package Controller.Admin.User;

import Dao.UserDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

public class ServletUserInfo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String student_code = request.getParameter("student_code");
        UserDao userDao = new UserDao();
        request.setAttribute("user", userDao.getUserByCode(student_code));
        request.getRequestDispatcher("/WEB-INF/views/admin/user-info.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
