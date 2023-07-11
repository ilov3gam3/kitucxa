package Controller.Auth;

import Controller.Mail;
import Dao.UserDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServletForgotPassword extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/auth/forgot-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String student_code = request.getParameter("student_code");
        String uuid = UUID.randomUUID().toString();
        if (new UserDao().checkEmailAndStudentCode(email, student_code)){
            if (new UserDao().updateUUIDByEmail(email, uuid)){
                String host = Mail.getHost(request);
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.submit(() -> {
                    try {
                        String html = "<h1>Nhấp vào <a href = '" + host + "/reset-password?key=" + uuid + "' target='_blank'>đây</a> để lấy lại mật khẩu</h1>";
                        Mail.send(email, "Lấy lại mật khẩu", html);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
                executorService.shutdown();
                request.setAttribute("success", "Vui lòng kiểm tra email của bạn.");
            } else {
                request.setAttribute("error", "Đã có lỗi xảy ra.");
            }
        } else {
            request.setAttribute("error", "Mã sinh viên và email không khớp với tài khoản nào.");
        }
        request.getRequestDispatcher("/WEB-INF/views/auth/forgot-password.jsp").forward(request, response);
    }
}
