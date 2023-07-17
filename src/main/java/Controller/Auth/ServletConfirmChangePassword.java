package Controller.Auth;

import Dao.UserDao;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ServletConfirmChangePassword extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String key = req.getParameter("key");
        String password = req.getParameter("password");
        if (new UserDao().changePassword(key, password)){
            req.getSession().setAttribute("session_mess", "success|Thay đổi mật khẩu thành công.");
        } else {
            req.getSession().setAttribute("session_mess", "error|Có lỗi đã xảy ra.");
        }
        resp.sendRedirect(req.getContextPath());
    }
}
