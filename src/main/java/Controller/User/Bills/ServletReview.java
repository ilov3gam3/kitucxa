package Controller.User.Bills;

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
        String bill_id  = req.getParameter("bill_id");
        Bill bill = new BillsDao().getBillById(Integer.parseInt(bill_id));
        req.setAttribute("bill", bill);
        req.getRequestDispatcher("/WEB-INF/views/user/make-review.jsp").forward(req, resp);
    }
}
