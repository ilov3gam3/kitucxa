package Controller.Admin.ExtraBill;

import Controller.Mail;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServletSendMailExtraBill extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String extra_bill_id = req.getParameter("extra_bill_id");
        String host = Mail.getHost(req);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                String html = "<h1>Xin chào " + name + ", chào mừng bạn đến với kí túc xá FPT. Để kích hoạt tài khoản vui lòng nhấn vào <a href='" + host + "/active/" + uuid + "'>đây.</a></h1>";
                Mail.send(email, "Xác nhận tài khoản", html);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }
}
