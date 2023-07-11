package Controller.User.ChangeRoom;

import Controller.Mail;
import Dao.BillsDao;
import Dao.ChangeDao;
import Dao.RoomsDao;
import Dao.UserDao;
import Model.Bill;
import Model.Room;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServletChangeRoom extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("bill_id"));
        Bill bill = new BillsDao().getBillById(id);
        ArrayList<Room> rooms = new RoomsDao().getAllRoomsWithNumbers(new String[]{bill.start, bill.end});
        req.setAttribute("rooms", rooms);
        req.setAttribute("bill", bill);
        req.getRequestDispatcher("/WEB-INF/views/user/change-room.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int room_id = Integer.parseInt(req.getParameter("to_room"));
        int bill_id = Integer.parseInt(req.getParameter("bill_id"));
        String reason = req.getParameter("reason");
        int check = new ChangeDao().addChange(bill_id, reason, room_id);
        if (check > 0){
            String host = Mail.getHost(req);

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(() -> {
                Bill bill = new BillsDao().getBillById(bill_id);
                User user = new UserDao().findUserById(bill.user_id);
                try {
                    String html = "<h1>Xin chào " + user.name + ", chúng tôi đã nhận được yêu cầu chuyển phòng của bạn. Nhấn vào <a href='" + host + "/user/view-changes?change_id=" + check + "'>đây.</a> để xem yêu cầu chuyển phòng của bạn.</h1>";
                    Mail.send(user.email, "Chuyển phòng", html);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            executorService.shutdown();

            req.getSession().setAttribute("session_mess","success|Gửi đơn đổi phòng thành công, chờ admin xử lý");
        }else {
            req.getSession().setAttribute("session_mess","error|Gửi đơn đổi phòng không thành công, chờ admin xử lý");
        }
        resp.sendRedirect(req.getContextPath() + "/user/bills");
    }
}
