package Controller;

import Dao.UserDao;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ServletVerifyMail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getParameter("key");
        UserDao userDao = new UserDao();
        User user = userDao.findUserBy_key(key);
        if (user == null){
            request.setAttribute("message", "Key không tồn tại.");
        } else {
            if (user.isVerified()){
                request.setAttribute("message", "Tài khoản này đã được xác thực.");
            } else { // chưa xác thực
                if (userDao.activeById(user.id)){
                    request.setAttribute("message", "Xác thực tài khoản thành công");
                } else {
                    request.setAttribute("message", "Xác thực tài khoản thất bại, vui lòng liên hệ admin.");

                }
            }
        }
        request.getRequestDispatcher("/WEB-INF/views/auth/confirm-email.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
