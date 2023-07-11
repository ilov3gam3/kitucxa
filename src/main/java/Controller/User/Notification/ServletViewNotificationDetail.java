package Controller.User.Notification;

import Dao.NotificationDao;
import Model.Notification;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ServletViewNotificationDetail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Notification notification = new NotificationDao().getNotificationById(id);
        req.setAttribute("notification", notification);
        req.getRequestDispatcher("/WEB-INF/views/user/view-notification-detail.jsp").forward(req, resp);
    }
}
