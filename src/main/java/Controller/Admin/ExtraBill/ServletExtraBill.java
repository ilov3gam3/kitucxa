package Controller.Admin.ExtraBill;

import Dao.BillsDao;
import Dao.ExtraBillDao;
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
        req.setAttribute("current", current);
        req.getRequestDispatcher("/WEB-INF/views/admin/extra-bills.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        String electricity = req.getParameter("electricity");
        String electricity_price = req.getParameter("electricity_price");
        String water = req.getParameter("water");
        String water_price = req.getParameter("water_price");
        boolean status = Boolean.parseBoolean(req.getParameter("status"));
        if (method.equals("create")){
            String room_id = req.getParameter("room_id");
            String start = req.getParameter("start");
            String end = req.getParameter("end");
            if (new ExtraBillDao().create(room_id, start, end, electricity, electricity_price, water, water_price, status)){
                req.getSession().setAttribute("session_mess", "success|Cập nhật thành công.");
            }else {
                req.getSession().setAttribute("session_mess", "error|Cập nhật không thành công.");
            }
        } else {
            String extra_bill_id = req.getParameter("extra_bill_id");
            if (new ExtraBillDao().update(extra_bill_id, electricity, electricity_price, water, water_price, status)){
                req.getSession().setAttribute("session_mess", "success|Thêm mới thành công.");
            } else {
                req.getSession().setAttribute("session_mess", "error|Thêm mới không thành công.");
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
