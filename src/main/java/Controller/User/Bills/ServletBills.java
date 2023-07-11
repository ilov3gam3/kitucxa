package Controller.User.Bills;

import Controller.Mail;
import Dao.BillsDao;
import Dao.CancelDao;
import Dao.UserDao;
import Model.Bill;
import Model.Status;
import Model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServletBills extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("session_mess") != null){
            String session_mess = (String) request.getSession().getAttribute("session_mess");
            request.setAttribute(session_mess.split("\\|")[0], session_mess.split("\\|")[1]);
            request.getSession().removeAttribute("session_mess");
        }
        User user = (User) request.getSession().getAttribute("user");
        int user_id = user.id;
        ArrayList<Bill> arrayList = new BillsDao().getAllBillsOfUser(user_id);
        request.setAttribute("bills", arrayList);
        request.getRequestDispatcher("/WEB-INF/views/user/bills.jsp").forward(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // huỷ bill
        int bill_id = Integer.parseInt(request.getParameter("bill_id"));
        String reason = request.getParameter("reason");
        int check = new CancelDao().addCancel(bill_id, reason);
        if (check > 0){
            String host = Mail.getHost(request);

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(() -> {
                Bill bill = new BillsDao().getBillById(bill_id);
                User user = new UserDao().findUserById(bill.user_id);
                try {
                    String html = "<h1>Xin chào " + user.name + ", chúng tôi đã nhận được yêu cầu huỷ phòng của bạn. Nhấn vào <a href='" + host + "/user/view-cancels?cancel_id=" + check + "'>đây.</a> để xem yêu cầu chuyển phòng của bạn.</h1>";
                    Mail.send(user.email, "Huỷ phòng", html);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            executorService.shutdown();

            request.getSession().setAttribute("session_mess", "success|Gửi yêu cầu huỷ thành công, vui lòng chờ admin xét duyệt.");
        } else {
            request.getSession().setAttribute("session_mess", "error|Gửi yêu cầu huỷ không thành công, vui lòng liên hệ admin.");
        }
        response.sendRedirect(request.getContextPath() + "/user/bills");
    }
}
