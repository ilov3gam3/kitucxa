package Controller.Admin.ChangeRoom;

import Dao.BillsDao;
import Dao.ChangeDao;
import Dao.RoomsDao;
import Model.Change;
import Model.Status;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

public class ServletViewChangeRooms extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("session_mess")!=null){
            String session_mess = (String) req.getSession().getAttribute("session_mess");
            req.setAttribute(session_mess.split("\\|")[0], session_mess.split("\\|")[1]);
            req.getSession().removeAttribute("session_mess");
        }
        ArrayList<Change> arrayList = new ChangeDao().getAllChangeRoom();
        req.setAttribute("changes", arrayList);
        req.getRequestDispatcher("/WEB-INF/views/admin/manage-changes.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int status = Integer.parseInt(req.getParameter("status"));
        int change_id = Integer.parseInt(req.getParameter("change_id"));
        int bill_id = Integer.parseInt(req.getParameter("bill_id"));
        int user_id = Integer.parseInt(req.getParameter("user_id"));
        String admin_reason = req.getParameter("admin_reason");
        if (Status.ACCEPTED.getValue() == status){ // chuyển đồng ý
            String[] semesters = new String[]{req.getParameter("start"),req.getParameter("end") };
            int room_to_id = Integer.parseInt(req.getParameter("room_to_id"));
            if (new RoomsDao().get1RoomWithNumbers(room_to_id, semesters).getNumber() < 4){
                ChangeDao changeDao = new ChangeDao();
                BillsDao billsDao = new BillsDao();
                boolean check_change = changeDao.changeStatus(change_id, Status.ACCEPTED.getValue(), admin_reason);
                boolean bill_check = billsDao.changeStatus(bill_id, Status.MOVED);
                if (check_change && bill_check){
                    if (billsDao.moveToRoom(user_id, semesters, room_to_id) > 0){
                        req.getSession().setAttribute("session_mess", "success|Đổi trạng thái thành công.");
                    } else {
                        req.getSession().setAttribute("session_mess", "error|Đã có lỗi xảy ra.");
                    }
                } else {
                    req.getSession().setAttribute("session_mess", "error|Đã có lỗi xảy ra.");
                }
            } else {
                req.getSession().setAttribute("session_mess", "warning|Phòng này đã đủ người.");
            }
        } else if (Status.DECLINED.getValue() == status){ // chuyển không đồng ý
            if (new ChangeDao().changeStatus(change_id, Status.DECLINED.getValue(), admin_reason)){
                req.getSession().setAttribute("session_mess", "success|Đổi trạng thái thành công.");
            } else {
                req.getSession().setAttribute("session_mess", "error|Đã có lỗi xảy ra.");
            }
        } else if (Status.PENDING.getValue() == status){ // chuyển thành đang chờ
            if (new ChangeDao().changeStatus(change_id, Status.PENDING.getValue(), admin_reason)){
                req.getSession().setAttribute("session_mess", "success|Đổi trạng thái thành công.");
            } else {
                req.getSession().setAttribute("session_mess", "error|Đã có lỗi xảy ra.");
            }
        }
        resp.sendRedirect(req.getContextPath() + "/admin/manage-change-rooms");
    }
}
