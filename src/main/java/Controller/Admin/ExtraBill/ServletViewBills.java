package Controller.Admin.ExtraBill;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ServletViewBills extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String room_id = req.getParameter("room_id");
        String start = req.getParameter("start");
        String end = req.getParameter("end");
        System.out.println(room_id);
        System.out.println(start);
        System.out.println(end);
    }
}
