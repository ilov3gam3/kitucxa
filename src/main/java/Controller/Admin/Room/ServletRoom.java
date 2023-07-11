package Controller.Admin.Room;

import Dao.BuildingDao;
import Dao.FloorDao;
import Dao.RoomsDao;
import Model.Building;
import Model.Floor;
import Model.Room;
import Model.Semester;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class ServletRoom extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int year;
        String semester;
        String[] semesters;
        if (request.getSession().getAttribute("session_mess") != null){
            String session_mess = (String) request.getSession().getAttribute("session_mess");
            request.setAttribute(session_mess.split("\\|")[0], session_mess.split("\\|")[1]);
            request.getSession().removeAttribute("session_mess");
        }
        if (request.getSession().getAttribute("session_mess_year_sem") != null){
            String session_mess = (String) request.getSession().getAttribute("session_mess_year_sem");
            year = Integer.parseInt(session_mess.split("\\|")[0]);
            semester = session_mess.split("\\|")[1];
            semesters = new String[]{year + "-" + Semester.valueOf(semester + "_start").getDetail(), year + "-" + Semester.valueOf(semester + "_end").getDetail()};
        } else {
            year = Calendar.getInstance().get(Calendar.YEAR);
            semester = Semester.getCurrentSemesterAsString();
            semesters = Semester.getCurrentSemester();
        }
        ArrayList<Room> arrayList = new RoomsDao().getAllRoomsWithNumbers(semesters);
        ArrayList<Floor> floors = new FloorDao().getAllFloorsWithBuildingName();
        String floor_json = "";
        for (int i = 0; i < floors.size(); i++) {
            if (i == 0){
                floor_json += "[";
            }
            if (i == floors.size() - 1){
                floor_json += floors.get(i);
            } else {
                floor_json += floors.get(i) + ", ";
            }
        }
        floor_json += "]";
        ArrayList<Building> buildings = new BuildingDao().getAllBuilding();
        String building_json = "";
        for (int i = 0; i < buildings.size(); i++) {
            if (i == 0){
                building_json += "[";
            }
            if (i == buildings.size() -1 ){
                building_json += buildings.get(i);
            } else {
                building_json += buildings.get(i) + ", ";
            }
        }
        building_json += "]";
        request.setAttribute("year", year);
        request.setAttribute("semester", semester);
        request.setAttribute("rooms", arrayList);
        request.setAttribute("floors", floors);
        request.setAttribute("floors_json", floor_json);
        request.setAttribute("buildings", buildings);
        request.setAttribute("buildings_json", building_json);
        request.getRequestDispatcher("/WEB-INF/views/admin/manage-rooms.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("method").equals("add")){
            int floor_id = Integer.parseInt(request.getParameter("floor_id"));
            boolean is_available =Boolean.parseBoolean(request.getParameter("is_available"));
            int price = Integer.parseInt(request.getParameter("price"));
            if (new RoomsDao().createRoom(floor_id, is_available, price)){
                request.getSession().setAttribute("session_mess", "success|Thêm mới phòng thành công!");
                response.sendRedirect(request.getContextPath() + "/admin/manage-rooms");
            } else {
                request.getSession().setAttribute("session_mess", "error|Thêm mới phòng không thành công!");
                response.sendRedirect(request.getContextPath() + "/admin/manage-rooms");
            }
        } else if (request.getParameter("method").equals("semester")){
            int year = Integer.parseInt(request.getParameter("year"));
            String semester = request.getParameter("semester");
            request.getSession().setAttribute("session_mess_year_sem", year + "|" + semester);
            response.sendRedirect(request.getContextPath() + "/admin/manage-rooms");
        }

    }
}
