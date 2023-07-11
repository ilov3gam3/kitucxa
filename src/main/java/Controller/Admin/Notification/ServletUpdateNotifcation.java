package Controller.Admin.Notification;

import Dao.NotificationDao;
import Model.Notification;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

public class ServletUpdateNotifcation extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Notification notification = new NotificationDao().getNotificationById(id);
        request.setAttribute("notification", notification);
        request.getRequestDispatcher("/WEB-INF/views/admin/update-notification.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String content = request.getParameter("content");
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        if (new NotificationDao().updateNotification(id, name, content, start, end)){
            request.getSession().setAttribute("session_mess", "success|Cập nhật thông báo thành công.");
        } else {
            request.getSession().setAttribute("session_mess", "error|Cập nhật thông báo không thành công.");
        }
        response.sendRedirect(request.getContextPath() + "/admin/manage-notification");
    }
}
