package Controller.User.BookRoom;

import Dao.RoomsDao;
import Model.Room;
import Model.Semester;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class ServletViewRooms extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int year;
        String semester;
        String[] semesters;

        if (request.getSession().getAttribute("session_mess_year_sem") != null){
            String session_mess = (String) request.getSession().getAttribute("session_mess_year_sem");
            year = Integer.parseInt(session_mess.split("\\|")[0]);
            semester = session_mess.split("\\|")[1];
            semesters = new String[]{year + "-" + Semester.valueOf(semester + "_start").getDetail(), year + "-" + Semester.valueOf(semester + "_end").getDetail()};
        } else {
            String year_semester = Semester.getNextSemester();
            year = Integer.parseInt(year_semester.split("-")[0]);
            semester = year_semester.split("-")[1];
            semesters = new String[]{year + "-" +Semester.valueOf(semester + "_start").getDetail(), year + "-" + Semester.valueOf(semester + "_end").getDetail()};
        }
        if (request.getSession().getAttribute("session_mess") != null){
            String session_mess = (String) request.getSession().getAttribute("session_mess");
            request.setAttribute(session_mess.split("\\|")[0], session_mess.split("\\|")[1]);
            request.getSession().removeAttribute("session_mess");
            request.setAttribute("year", year);
            request.setAttribute("semester", semester);
        } else {
            ArrayList<Room> arrayList = new RoomsDao().getAllRoomsWithNumbers(semesters);
            request.setAttribute("rooms", arrayList);
            request.setAttribute("year", year);
            request.setAttribute("semester", semester);
        }
        request.getRequestDispatcher("/WEB-INF/views/user/book-room.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        if (userViewDate.compareTo(date) > 0){
            request.getSession().setAttribute("session_mess_year_sem", year + "|" + semester);
        } else {
            request.getSession().setAttribute("session_mess_year_sem", year + "|" + semester);
            request.getSession().setAttribute("session_mess", "warning|Bạn không thể xem thông tin phòng từ kì hiện tại trở về trước.");
        }
        response.sendRedirect(request.getContextPath() + "/user/book-rooms");
    }
}
