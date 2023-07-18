package Controller;

import Dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServletRegister extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String student_code = request.getParameter("student_code").toUpperCase();
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        if (!email.endsWith("@fpt.edu.vn")){
            request.setAttribute("error", "Đăng kí không thành công! Mail phải là mail fpt.");
            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
        } else {
            if (student_code.matches("^[A-Za-z]{2}\\d{6}$")){
                String address = request.getParameter("address");
                String tel = request.getParameter("tel");
                String birthday = request.getParameter("birthday");
                String password = request.getParameter("password");
                String uuid = UUID.randomUUID().toString();
                UserDao userDao = new UserDao();
                int check = userDao.checkUserInfoDuplicate(student_code, email, tel);
                switch (check){
                    case 0:{
                        if (userDao.addUser(student_code,name, email, address, tel, birthday, password, uuid)){
                            String host = Mail.getHost(request);
                            ExecutorService executorService = Executors.newSingleThreadExecutor();
                            executorService.submit(() -> {
                                try {
                                    String html = "<h1>Xin chào " + name + ", chào mừng bạn đến với kí túc xá FPT. Để kích hoạt tài khoản vui lòng nhấn vào <a href='" + host + "/active?key=" + uuid + "'>đây.</a></h1>";
                                    Mail.send(email, "Xác nhận tài khoản", html);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                            executorService.shutdown();
                            request.setAttribute("success", "Đăng kí thành công, vui lòng check mail để hoàn tất đăng kí, nếu không thấy mail, vui lòng liên hệ admin!");
                            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
                        } else {
                            request.setAttribute("error", "Đăng kí không thành công! Vui lòng đăng kí lại hoặc liên hệ Admin");
                            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
                        }
                        break;
                    }
                    case 1:{
                        request.setAttribute("error", "Mã số sinh viên đã tồn tại!");
                        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
                        break;
                    }
                    case 2:{
                        request.setAttribute("error", "Email đã tồn tại!");
                        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
                        break;
                    }
                    case 3:{
                        request.setAttribute("error", "Số điện thoại đã tồn tại!");
                        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
                        break;
                    }
                }
            } else {
                request.setAttribute("error", "Mã sinh viên không hợp lệ!");
                request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
            }
        }
    }
}
