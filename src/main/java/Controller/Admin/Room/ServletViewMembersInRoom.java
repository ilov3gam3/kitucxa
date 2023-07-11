package Controller.Admin.Room;

import Dao.BillsDao;
import Dao.RoomsDao;
import Model.Semester;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

public class ServletViewMembersInRoom extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int room_id = Integer.parseInt(req.getParameter("room_id"));
        int year = Integer.parseInt(req.getParameter("year"));
        String semester = req.getParameter("semester");
        String start = Semester.getDate(year, Semester.valueOf(semester + "_start"));
        String end = Semester.getDate(year, Semester.valueOf(semester + "_end"));
        ArrayList<User> arrayList = new RoomsDao().getUsersInRoom(room_id, start, end);
        req.setAttribute("students", arrayList);
        req.getRequestDispatcher("/WEB-INF/views/admin/view-student-in-room.jsp").forward(req, resp);
    }
}
