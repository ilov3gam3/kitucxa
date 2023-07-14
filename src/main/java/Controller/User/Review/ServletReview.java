package Controller.User.Review;

import Dao.BillsDao;
import Model.Bill;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ServletReview extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int bill_id = Integer.parseInt(req.getParameter("bill_id"));
        Bill bill = new BillsDao().getBillById(bill_id);
        req.setAttribute("bill", bill);
        req.getRequestDispatcher("/WEB-INF/views/user/review.jsp").forward(req, resp);
    }
}
