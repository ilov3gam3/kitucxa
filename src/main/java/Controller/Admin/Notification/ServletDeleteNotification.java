package Controller.Admin.Notification;

import Dao.NotificationDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

public class ServletDeleteNotification extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        if (new NotificationDao().deleteNotification(id)){
            request.getSession().setAttribute("session_mess", "success|Xoá thông báo thành công.");
        } else {
            request.getSession().setAttribute("session_mess", "error|Xoá thông báo không thành công.");
        }
        response.sendRedirect(request.getContextPath() + "/admin/manage-notification");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
