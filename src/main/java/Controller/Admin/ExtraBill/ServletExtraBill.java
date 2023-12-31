package Controller.Admin.ExtraBill;

import Dao.BillsDao;
import Dao.ExtraBillDao;
import Model.Bill;
import Model.Config;
import Model.ExtraBill;
import Model.Semester;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

public class ServletExtraBill extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("session_mess") != null){
            String session_mess = (String) req.getSession().getAttribute("session_mess");
            req.setAttribute(session_mess.split("\\|")[0], session_mess.split("\\|")[1]);
            req.getSession().removeAttribute("session_mess");
        }
        String view_semester;
        String[] view_semesters;
        if (req.getParameter("year") != null && req.getParameter("semester") != null){ // có para
            int year = Integer.parseInt(req.getParameter("year"));
            String semester = req.getParameter("semester");
            view_semester = year + "-" + semester;
            view_semesters = new String[]{Semester.getDate(year, Semester.valueOf(semester + "_start")), Semester.getDate(year, Semester.valueOf(semester + "_end"))};
        } else { // default xem kì trước.
           view_semester = Semester.getPreviousSemester();
           view_semesters = new String[]{view_semester.split("-")[0] + "-" + Semester.valueOf(view_semester.split("-")[1] + "_start").getDetail(),
                   view_semester.split("-")[0] + "-" + Semester.valueOf(view_semester.split("-")[1] + "_end").getDetail()};
        }
        ArrayList<ExtraBill> arrayList = new BillsDao().getRoomsInSemester(view_semesters);
//        String semester = Semester.getSemester(semesters[0], semesters[1]);


        req.setAttribute("rooms", arrayList);
        req.setAttribute("semesters", view_semesters);
        req.setAttribute("semester", view_semester.split("-")[1]);
        req.setAttribute("year", view_semester.split("-")[0]);


        String[] currents = Semester.getCurrentSemester();
        String current = Semester.getSemester(currents[0], currents[1]);
        req.setAttribute("currents", currents);
        req.setAttribute("current1", current);
        /*req.setAttribute("max_electricity", Integer.parseInt(Config.map.get("max_electricity")));
        req.setAttribute("max_water", Integer.parseInt(Config.map.get("max_water")));*/
        int[] limits = new BillsDao().getLimit();
        req.setAttribute("max_electricity", limits[0]);
        req.setAttribute("max_water", limits[1]);
        req.getRequestDispatcher("/WEB-INF/views/admin/extra-bills.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        String electricity = req.getParameter("electricity");
        String electricity_end = req.getParameter("electricity_end");
        String electricity_price = req.getParameter("electricity_price");
        String water = req.getParameter("water");
        String water_end = req.getParameter("water_end");
        String water_price = req.getParameter("water_price");
        boolean status = Boolean.parseBoolean(req.getParameter("status"));
        if (!electricity.equals("") && !electricity_end.equals("") && !water.equals("") && !water_end.equals("")){
            if (Integer.parseInt(electricity_end) < Integer.parseInt(electricity)){
                req.getSession().setAttribute("session_mess", "error|Số điện cuối kì phải lớn hơn số điện đầu kì.");
            } else if (Integer.parseInt(water_end) < Integer.parseInt(water)){
                req.getSession().setAttribute("session_mess", "error|Số khối nước cuối kì phải lớn hơn số khối nước đầu kì.");
            } else {
                if (method.equals("create")){
                    String room_id = req.getParameter("room_id");
                    String start = req.getParameter("start");
                    String end = req.getParameter("end");
                    if (new ExtraBillDao().create(room_id, start, end, electricity, electricity_price, water, water_price, status, electricity_end, water_end)){
                        req.getSession().setAttribute("session_mess", "success|Thêm hoá đơn thành công.");
                    }else {
                        req.getSession().setAttribute("session_mess", "error|Thêm hoá đơn không thành công.");
                    }
                } else {
                    String extra_bill_id = req.getParameter("extra_bill_id");
                    if (new ExtraBillDao().update(extra_bill_id, electricity, electricity_price, water, water_price, status, electricity_end, water_end)) {
                        req.getSession().setAttribute("session_mess", "success|Cập nhật thành công.");
                    } else {
                        req.getSession().setAttribute("session_mess", "error|Cập nhật không thành công.");
                    }
                }
            }
        } else {
            if (method.equals("create")){
                String room_id = req.getParameter("room_id");
                String start = req.getParameter("start");
                String end = req.getParameter("end");
                if (new ExtraBillDao().create(room_id, start, end, electricity, electricity_price, water, water_price, status, electricity_end, water_end)){
                    req.getSession().setAttribute("session_mess", "success|Thêm hoá đơn thành công.");
                }else {
                    req.getSession().setAttribute("session_mess", "error|Thêm hoá đơn không thành công.");
                }
            } else {
                String extra_bill_id = req.getParameter("extra_bill_id");
                if (new ExtraBillDao().update(extra_bill_id, electricity, electricity_price, water, water_price, status, electricity_end, water_end)) {
                    req.getSession().setAttribute("session_mess", "success|Cập nhật thành công.");
                } else {
                    req.getSession().setAttribute("session_mess", "error|Cập nhật không thành công.");
                }
            }
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
