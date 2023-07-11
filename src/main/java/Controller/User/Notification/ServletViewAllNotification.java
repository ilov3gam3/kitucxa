package Controller.User.Notification;

import Dao.NotificationDao;
import Model.Notification;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

public class ServletViewAllNotification extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Notification> notifications = new NotificationDao().getAllNotifications();
        request.setAttribute("notifications", notifications);
        request.getRequestDispatcher("/WEB-INF/views/user/view-all-notifications.jsp").forward(request, response);
    }
}
