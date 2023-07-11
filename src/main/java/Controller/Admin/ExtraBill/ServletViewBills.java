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
}
