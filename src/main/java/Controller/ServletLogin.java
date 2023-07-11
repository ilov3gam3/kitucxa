package Controller;

import Dao.UserDao;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class ServletLogin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stu_code_or_mail = request.getParameter("student_code_or_email");
        String password = request.getParameter("password");
        User user = null;
        try {
            user = new UserDao().login(stu_code_or_mail, password);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("login_fail", "Có lỗi xảy ra.");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
        }
        if (user == null){
            request.setAttribute("login_fail", "Tài khoản của bạn chưa đúng.");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
        } else {
            if (!user.isVerified()){
                request.setAttribute("login_fail", "Email của bạn chưa được xác thực!");
                request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            } else {
                request.getSession().setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/");
            }
        }
    }
}
