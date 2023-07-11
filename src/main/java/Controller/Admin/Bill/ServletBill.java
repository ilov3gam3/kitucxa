package Controller.Admin.Bill;

import Controller.Mail;
import Dao.BillsDao;
import Dao.RoomsDao;
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

public class ServletBill extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("session_mess") != null) {
            String session_mess = (String) request.getSession().getAttribute("session_mess");
            request.setAttribute(session_mess.split("\\|")[0], session_mess.split("\\|")[1]);
            request.getSession().removeAttribute("session_mess");
        }
        ArrayList<Bill> arrayList = new BillsDao().getAllBills();
        request.setAttribute("bills", arrayList);
        request.getRequestDispatcher("/WEB-INF/views/admin/bills.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int room_id = Integer.parseInt(request.getParameter("room_id"));
        String[] semesters = new String[]{request.getParameter("start"), request.getParameter("end")};
        int status = Integer.parseInt(request.getParameter("status"));
        if (status == 1 && new RoomsDao().get1RoomWithNumbers(room_id, semesters).getNumber() == 4) {
            request.getSession().setAttribute("session_mess", "warning|Phòng này đã đủ người.");
        } else {
            int bill_id = Integer.parseInt(request.getParameter("bill_id"));
            Status status1 = Status.getByValue(status);
            if (new BillsDao().changeStatus(bill_id, status1)) {
                String host = Mail.getHost(request);
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.submit(() -> {
                    try {
                        Bill bill = new BillsDao().getBillById(bill_id);
                        User user = new UserDao().findUserById(bill.user_id);
                        String html = "";
                        if (status1 == Status.ACCEPTED) {
                            html = "<h1>Xin chào " + user.name + ", Admin đã xác nhận phòng " + bill.room_name + " của kì " + bill.getSemester() + " năm " + bill.start.split(" ")[0].split(":")[0] + " của bạn. Nhấn vào <a href='" + host + "/user/bills?bill_id=" + bill_id + "'> đây </a> để xem hoá đơn của bạn.</h1>";
                        } else if (status1 == Status.DECLINED) {
                            html = "<h1>Xin chào " + user.name + ", Admin đã huỷ đơn phòng " + bill.room_name + " của kì " + bill.getSemester() + " năm " + bill.start.split(" ")[0].split(":")[0] + " của bạn. Nhấn vào <a href='" + host + "/user/bills?bill_id=" + bill_id + "'> đây </a> để xem hoá đơn của bạn.</h1>";
                        } else if (status1 == Status.PENDING) {
                            html = "<h1>Xin chào " + user.name + ", Admin chuyển trạng thái đơn phòng " + bill.room_name + " của kì " + bill.getSemester() + " của bạn thành <span  style='color: red;'>chưa thanh toán</span>. Nhấn vào <a href='" + host + "/user/bills?bill_id=" + bill_id + "'> đây </a> để xem hoá đơn của bạn.</h1>";
                        }
                        Mail.send(user.email, "Đăng kí phòng", html);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                executorService.shutdown();

                request.getSession().setAttribute("session_mess", "success|Thay đổi thành công");
            } else {
                request.getSession().setAttribute("session_mess", "error|Thay đổi thất bại");
            }
        }
        response.sendRedirect(request.getContextPath() + "/admin/manage-bills");
    }
}
