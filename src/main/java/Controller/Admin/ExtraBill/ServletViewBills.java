package Controller.Admin.ExtraBill;

import Dao.BillsDao;
import Dao.ExtraBillDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

public class ServletViewBills extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String room_id = req.getParameter("room_id");
        String start = req.getParameter("start");
        String end = req.getParameter("end");
        ArrayList<Integer> arrayList= new BillsDao().getBillIdBy(room_id, start, end);
        String query = "?bill_id=";
        for (int i = 0; i < arrayList.size(); i++) {
            if (i == arrayList.size()-1){
                query += arrayList.get(i);
                break;
            }
            query+=arrayList.get(i) + "-";
        }
        resp.sendRedirect(req.getContextPath() + "/admin/manage-bills" + query);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int max_electricity = Integer.parseInt(req.getParameter("max_electricity"));
        int max_water = Integer.parseInt(req.getParameter("max_water"));
        if (new BillsDao().changeLimit(max_electricity, max_water)){
            req.getSession().setAttribute("session_mess", "success|Cập nhật thành công.");
        } else {
            req.getSession().setAttribute("session_mess", "error|Cập nhật không thành công.");
        }
        if (req.getParameter("year") != null && req.getParameter("semester") != null){
            int year = Integer.parseInt(req.getParameter("year"));
            String semester = req.getParameter("semester");
            resp.sendRedirect(req.getContextPath() + "/admin/manage-extra-bills?year="+year+"&semester="+ semester);
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/manage-extra-bills");
        }
    }
}
