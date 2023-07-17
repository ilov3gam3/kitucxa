package Controller.Admin.User;

import Dao.UserDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

public class ServletUserInfo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("session_mess") != null){
            String session_mess = (String) request.getSession().getAttribute("session_mess");
            request.setAttribute(session_mess.split("\\|")[0], session_mess.split("\\|")[1]);
            request.getSession().removeAttribute("session_mess");
        }
        String student_code = request.getParameter("student_code");
        UserDao userDao = new UserDao();
        request.setAttribute("user1", userDao.getUserByCode(student_code));
        request.getRequestDispatcher("/WEB-INF/views/admin/user-info.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String student_code = request.getParameter("student_code").toUpperCase();
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String tel = request.getParameter("tel");
        String new_password = request.getParameter("new_password");
        String verify = request.getParameter("verify");
        String admin = request.getParameter("admin");
        String id = request.getParameter("id");
        UserDao userDao = new UserDao();
        if (new_password.equals("")){
            if (userDao.adminUpdateNoPass(student_code, name, email, address, tel, verify, admin, id)){
                request.getSession().setAttribute("session_mess", "success|Cập nhật thành công.");
            } else {
                request.getSession().setAttribute("session_mess", "error|Đã có lỗi xảy ra.");
            }
        } else {
            if (userDao.adminUpdate(student_code, name, email, address, tel,new_password, verify, admin, id)){
                request.getSession().setAttribute("session_mess", "success|Cập nhật thành công.");
            } else {
                request.getSession().setAttribute("session_mess", "error|Đã có lỗi xảy ra.");
            }
        }
        response.sendRedirect(request.getContextPath() + "/admin/manage-users");
    }
}
