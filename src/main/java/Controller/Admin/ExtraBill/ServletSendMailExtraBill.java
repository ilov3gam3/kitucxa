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
            int electricity_value = arrayList.get(i).electricity_end - arrayList.get(i).electricity - Integer.parseInt(Config.map.get("max_electricity"));
            if (electricity_value < 0){
                electricity_value = 0;
            }
            int water_value = arrayList.get(i).water_end - arrayList.get(i).water - Integer.parseInt(Config.map.get("max_water"));
            if (water_value < 0){
                water_value = 0;
            }
            int total = electricity_value * arrayList.get(i).electricity_price + water_value * arrayList.get(i).water_price;
            String table = "<style>\n" +
                    "    table, th, td {\n" +
                    "  border: 1px solid black;\n" +
                    "  border-collapse: collapse;\n" +
                    "}\n" +
                    "  </style>" +
                    "<table border='-1'>\n" +
                    "    <tr>\n" +
                    "      <th>Số điện đầu kì(KWh)</th>\n" +
                    "      <th>Số điện cuối kì(KWh)</th>\n" +
                    "      <th>Giá điện(vnđ)</th>\n" +
                    "      <th>Hạn mức điện</th>\n" +
                    "      <th>Số khối nước đầu kì(m³)</th>\n" +
                    "      <th>Số khối nước cuối kì(m³)</th>\n" +
                    "      <th>Giá nước(vnđ)</th>\n" +
                    "      <th>Hạn mức nước</th>\n" +
                    "      <th>Tổng cộng(vnđ)</th>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "      <td>" + arrayList.get(i).electricity + "</td>\n" +
                    "      <td>" + arrayList.get(i).electricity_end + "</td>\n" +
                    "      <td>" + arrayList.get(i).electricity_price + "</td>\n" +
                    "      <td>" + Config.map.get("max_electricity") + "</td>\n" +
                    "      <td>" + arrayList.get(i).water + "</td>\n" +
                    "      <td>" + arrayList.get(i).water_end + "</td>\n" +
                    "      <td>" + arrayList.get(i).water_price + "</td>\n" +
                    "      <td>" + Config.map.get("max_water") + "</td>\n" +
                    "      <td>" + total + "</td>\n" +
                    "\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "      <td style='text-align: center;' colspan='2'>" + electricity_value + "</td>\n" +
                    "      <td>" + arrayList.get(i).electricity_price + "</td>\n" +
                    "      <td></td>\n" +
                    "      <td style='text-align: center;' colspan='2'>" + water_value + "</td>\n" +
                    "      <td>" + arrayList.get(i).water_price + "</td>\n" +
                    "      <td></td>\n" +
                    "      <td>" + total + "</td>\n" +
                    "    </tr>\n" +
                    "  </table>";
            contents[i] = "<h1 style='display: inline-block; white-space: pre;'>Xin chào " + arrayList.get(i).username + ", sau đây là hoá đơn tiền điện, tiền nước của phòng " + arrayList.get(i).room_name + " trong kì " + Semester.getSemester(arrayList.get(i).start, arrayList.get(i).end) + ".</h1> <br>" +
                    table;
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
        req.getSession().setAttribute("session_mess", "success|Đã gửi mail.");
        if (req.getParameter("year") != null && req.getParameter("semester") != null) {
            int year = Integer.parseInt(req.getParameter("year"));
            String semester = req.getParameter("semester");
            resp.sendRedirect(req.getContextPath() + "/admin/manage-extra-bills?year=" + year + "&semester=" + semester);
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/manage-extra-bills");
        }
    }
}
