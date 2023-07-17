package Controller.User.BookRoom;

import Controller.Mail;
import Dao.BillsDao;
import Dao.RoomsDao;
import Model.Bill;
import Model.Room;
import Model.Semester;
import Model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServletRoomDetail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("session_mess") != null) {
            String session_mess = (String) request.getSession().getAttribute("session_mess");
            request.setAttribute(session_mess.split("\\|")[0], session_mess.split("\\|")[1]);
            request.getSession().removeAttribute("session_mess");
        }
        int room_id = Integer.parseInt(request.getParameter("id"));
        int year = Integer.parseInt(request.getParameter("year"));
        String semester = request.getParameter("semester");
        String viewDate = Semester.getDate(year, Semester.valueOf(semester + "_start"));
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date userViewDate;
        try {
            userViewDate = simpleDateFormat.parse(viewDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if (userViewDate.compareTo(date) > 0) {
            String[] semesters = new String[]{year + "-" + Semester.valueOf(semester + "_start").getDetail(), year + "-" + Semester.valueOf(semester + "_end").getDetail()};
            Room room = new RoomsDao().get1RoomWithNumbers(room_id, semesters);
            ArrayList<Bill> bills = new BillsDao().getReviewsOfRoom(room_id);
            request.setAttribute("room", room);
            request.setAttribute("year", year);
            request.setAttribute("semester", semester);
            request.setAttribute("bills", bills);
        } else {
            Room room = new Room();
            room.id = room_id;
            request.setAttribute("room", room);
            request.setAttribute("year", year);
            request.setAttribute("semester", semester);
            request.setAttribute("warning", "Bạn không thể xem thông tin phòng từ kì hiện tại trở về trước.");
        }
        request.getRequestDispatcher("/WEB-INF/views/user/book-room-detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        int user_id = user.id;
        int room_id = Integer.parseInt(request.getParameter("id"));
        int year = Integer.parseInt(request.getParameter("year"));
        String semester = request.getParameter("semester");
        String viewDate = Semester.getDate(year, Semester.valueOf(semester + "_start"));
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date userViewDate;
        try {
            userViewDate = simpleDateFormat.parse(viewDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if (userViewDate.compareTo(date) > 0) {
            String[] semesters = new String[]{year + "-" + Semester.valueOf(semester + "_start").getDetail(), year + "-" + Semester.valueOf(semester + "_end").getDetail()};
            int check = new BillsDao().checkUserRegisterRoom(user_id, semesters);
            if (check == -1) {
                request.getSession().setAttribute("session_mess", "error|Lỗi hệ thống, vui lòng liên hệ admin!");
            } else if (check > 0) {
                request.getSession().setAttribute("session_mess", "warning|Bạn đã đặt 1 phòng trong kì này rồi!");
            } else {
                int id = new BillsDao().registerARoom(user_id, semesters, room_id);
                if (id > 0) {
                    String host = Mail.getHost(request);
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.submit(() -> {
                        try {
                            Room room = new RoomsDao().getRoomById(room_id);
                            String html = "<h1>Xin chào " + user.name + ", chúng tôi đã nhận đơn đăng kí phòng " + room.name + " của kì " + semester + " năm " + year + " nhấn vào <a href='" + host + "/user/bills?bill_id=" + id + "'> đây </a> để xem hoá đơn của bạn.</h1>";
                            Mail.send(user.email, "Đăng kí phòng", html);
                        } catch (Exception e) {
                            e.printStackTrace(); // Or log the exception
                        }
                    });
                    executorService.shutdown();

                    request.getSession().setAttribute("session_mess", "success|Đặt phòng thành công!");
                } else {
                    request.getSession().setAttribute("session_mess", "error|Đặt phòng không thành công!");
                }
            }
        } else {
            request.getSession().setAttribute("session_mess", "warning1|Bạn không thể đặt phòng từ kì hiện tại trở về trước!");
        }
        response.sendRedirect(request.getRequestURI() + "?id=" + room_id + "&semester=" + semester + "&year=" + year);
    }
}
