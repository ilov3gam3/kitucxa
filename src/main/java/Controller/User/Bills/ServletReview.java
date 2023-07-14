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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int bill_id = Integer.parseInt(req.getParameter("bill_id"));
        int rate = Integer.parseInt(req.getParameter("rate"));
        String review = req.getParameter("review");
        if (new BillsDao().updateReview(bill_id, review, rate)){
            req.getSession().setAttribute("session_mess", "success|Gửi nhận xét thành công.");
        } else {
            req.getSession().setAttribute("session_mess", "error|Gửi nhận xét không thành công.");
        }
        resp.sendRedirect(req.getContextPath() + "/user/bills");
    }
}
