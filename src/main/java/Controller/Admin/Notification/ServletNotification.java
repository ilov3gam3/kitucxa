package Controller.Admin.Notification;

import Dao.NotificationDao;
import Model.Notification;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ServletNotification extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("session_mess") != null){
            String session_mess = (String) request.getSession().getAttribute("session_mess");
            request.setAttribute(session_mess.split("\\|")[0], session_mess.split("\\|")[1]);
            request.getSession().removeAttribute("session_mess");
        }
        ArrayList<Notification> notifications = new NotificationDao().getAllNotifications();
        request.setAttribute("notifications", notifications);
        request.getRequestDispatcher("/WEB-INF/views/admin/manage-notification.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String content = request.getParameter("content");
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        SimpleDateFormat datetimeLocalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date start_time;
        Date end_time;
        try {
            start_time = datetimeLocalFormat.parse(start);
            end_time = datetimeLocalFormat.parse(end);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if (start_time.compareTo(end_time)>0){
            request.getSession().setAttribute("session_mess", "error|Thời gian kết thúc phải lớn hơn thời gian bắt đầu.");
        } else {
            if (new NotificationDao().addNotification(name, content, start, end)){
                request.getSession().setAttribute("session_mess", "success|Thêm thông báo thành công.");
            } else {
                request.getSession().setAttribute("session_mess", "error|Thêm thông báo không thành công.");
            }
        }
        response.sendRedirect(request.getContextPath() + "/admin/manage-notification");
    }
}
