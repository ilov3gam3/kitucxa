package Controller.User.Profile;

import Controller.Mail;
import Dao.UserDao;
import Model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServletProfile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("session_para") != null){
            String session_mess = (String) request.getSession().getAttribute("session_para");
            request.setAttribute(session_mess.split("\\|")[0], session_mess.split("\\|")[1]);
            request.getSession().removeAttribute("session_para");
        }
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String current_password = request.getParameter("current_password");
        String new_password = request.getParameter("new_password");
        String re_password = request.getParameter("re_password");
        UserDao userDao = new UserDao();
        int id = ((User) request.getSession().getAttribute("user")).id;
        User user = (User) request.getSession().getAttribute("user");
        if (current_password.equals("") && new_password.equals("") && re_password.equals("")){
            if (userDao.update(name, user.password, id)){
                User new_user = userDao.findUserById(id);
                request.getSession().setAttribute("user", new_user);
                request.getSession().setAttribute("session_para", "success|Cập nhật thành công.");
            } else {
                request.getSession().setAttribute("session_para", "error|Cập nhật thất bại.");
            }
        } else {
            if (current_password.equals(user.password)){
                if (re_password.equals("") && new_password.equals("")){
                    request.getSession().setAttribute("session_para", "info|Vui lòng nhập mật khẩu mới.");
                } else {
                    String uuid = UUID.randomUUID().toString();
                    if (new_password.equals(re_password) && new UserDao().updateUUIDByEmail(user.email, uuid)){ // sẽ thực hiện đổi mk


                        String host = Mail.getHost(request);
                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        executorService.submit(() -> {
                            try {
                                String html = "<h1>Nhấp vào <a href = '" + host + "/confirm-change-password?key=" + uuid + "&password="+new_password+"' target='_blank'>đây</a> để xác nhận thay đổi.</h1>";
                                Mail.send(user.email, "Xác nhận thay đổi mật khẩu.", html);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        });
                        executorService.shutdown();
                        request.getSession().setAttribute("session_para", "success|Vui lòng kiểm tra email để hoàn tất thay đổi mật khẩu.");

                            /*if (userDao.update(name, new_password, id)){



                                User new_user = userDao.findUserById(id);
                                request.getSession().setAttribute("user", new_user);
                                request.getSession().setAttribute("session_para", "success|Cập nhật thành công.");
                            } else {
                                request.getSession().setAttribute("session_para", "error|Cập nhật thất bại.");
                            }*/
                    } else {
                        request.getSession().setAttribute("session_para", "error|Nhập lại mật khẩu không trùng khớp hoặc đã có lỗi xảy ra.");
                    }
                }
            } else {
                request.getSession().setAttribute("session_para", "error|Mật khẩu hiện tại sai.");
            }
        }
        response.sendRedirect(request.getContextPath() + "/user/profile");
    }
}
