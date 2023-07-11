package Controller.Auth;

import Dao.UserDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

public class ServletResetPassword extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getParameter("key");
        UserDao userDao = new UserDao();
        if (userDao.checkValidKey(key)){
        } else {
            request.setAttribute("error","Đường dẫn của bạn không hợp lệ hoặc đã hết hạn!");
        }
        request.getRequestDispatcher("/WEB-INF/views/auth/reset-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getParameter("key");
        String password = request.getParameter("password");
        String re_password = request.getParameter("re_password");
        if (password.equals(re_password)){
            UserDao userDao = new UserDao();
            if (userDao.checkValidKey(key)){
                if (userDao.changePassword(key, password)){
                    request.setAttribute("success","Thay đổi mật khẩu thành công.");
                    userDao.deleteKey(key);
                }
            } else {
                request.setAttribute("error","Đường dẫn của bạn không hợp lệ hoặc đã hết hạn!");
            }
        } else {
            request.setAttribute("error","Mật khẩu không trùng khớp!");
        }
        request.getRequestDispatcher("/WEB-INF/views/auth/reset-password.jsp").forward(request, response);
    }
}
