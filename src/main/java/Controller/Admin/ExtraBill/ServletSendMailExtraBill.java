package Controller.Admin.ExtraBill;

import Controller.Mail;
import Dao.ExtraBillDao;
import Model.Config;
import Model.ExtraBill;
import Model.Semester;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServletSendMailExtraBill extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String extra_bill_id = req.getParameter("extra_bill_id");
        String host = Mail.getHost(req);
        ExtraBillDao extraBillDao = new ExtraBillDao();
        ArrayList<ExtraBill> arrayList = extraBillDao.getExtraWithUsername(Integer.parseInt(extra_bill_id));
        String[] addresses = new String[arrayList.size()];
        String[] contents = new String[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            addresses[i] = arrayList.get(i).user_email;
            contents[i] = "<h1 style='display: inline-block; white-space: pre;'>Xin chào " + arrayList.get(i).username + ", sau đây là hoá đơn tiền điện, tiền nước của phòng " + arrayList.get(i).room_name + " trong kì " + Semester.getSemester(arrayList.get(i).start, arrayList.get(i).end) + ". <br>" +
                    "Tiền điện : ("+arrayList.get(i).electricity+"-"+ Config.map.get("max_electricity") +") * "+arrayList.get(i).electricity_price+" = "+ (arrayList.get(i).electricity - Integer.parseInt(Config.map.get("max_electricity"))) * arrayList.get(i).electricity_price +" <br>" +
                    "Tiền nước : ("+arrayList.get(i).water+"-"+ Config.map.get("max_water") +") * "+arrayList.get(i).water_price+" = "+ (arrayList.get(i).water - Integer.parseInt(Config.map.get("max_water"))) * arrayList.get(i).water_price +
                    "</h1>";
        }
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                for (int i = 0; i < addresses.length; i++) {
                    Mail.send(addresses[i], "Hoá đơn điện nước", contents[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        if (req.getParameter("year") != null && req.getParameter("semester") != null){
            int year = Integer.parseInt(req.getParameter("year"));
            String semester = req.getParameter("semester");
            resp.sendRedirect(req.getContextPath() + "/admin/manage-extra-bills?year="+year+"&semester="+ semester);
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/manage-extra-bills");
        }
    }
}
