package Controller.Admin.Room;

import Dao.BillsDao;
import Model.Bill;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

public class ServletViewAllRoomReview extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int room_id = Integer.parseInt(req.getParameter("room_id"));
        ArrayList<Bill> bills = new BillsDao().getReviewsOfRoom(room_id);
        req.setAttribute("bills", bills);
        req.getRequestDispatcher("/WEB-INF/views/admin/view-room-review.jsp").forward(req, resp);
    }
}
