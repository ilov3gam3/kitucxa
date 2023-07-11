package Controller.User.BookRoom;

import Dao.RoomsDao;
import Model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;

public class ServletVieUserInRoom extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int room_id = Integer.parseInt(request.getParameter("room_id"));
        String start_date = request.getParameter("start_date");
        String end_date = request.getParameter("end_date");
        ArrayList<User> arrayList = new RoomsDao().getUsersInRoom(room_id, start_date, end_date);
        request.setAttribute("users", arrayList);
        request.getRequestDispatcher("/WEB-INF/views/user/view-users-in-room.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
