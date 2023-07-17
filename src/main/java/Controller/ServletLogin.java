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
        if (request.getSession().getAttribute("session_mess") != null){
            String session_mess = (String) request.getSession().getAttribute("session_mess");
            request.setAttribute(session_mess.split("\\|")[0], session_mess.split("\\|")[1]);
            request.getSession().removeAttribute("session_mess");
        }
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
            request.setAttribute("error", "Có lỗi xảy ra.");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
        }
        if (user == null){
            request.setAttribute("error", "Tài khoản của bạn chưa đúng.");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
        } else {
            if (!user.isVerified()){
                request.setAttribute("warning", "Email của bạn chưa được xác thực!");
                request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            } else {
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("session_mess", "success|Đăng nhập thành công.");
                response.sendRedirect(request.getContextPath() + "/");
            }
        }
    }
}
