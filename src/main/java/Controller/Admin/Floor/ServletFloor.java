package Controller.Admin.Floor;

import Dao.BuildingDao;
import Dao.FloorDao;
import Model.Building;
import Model.Floor;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;

public class ServletFloor extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Floor> arrayList = new FloorDao().getAllFloorsWithBuildingName();
        ArrayList<Building> buildings = new BuildingDao().getAllBuilding();
        if (request.getSession().getAttribute("session_mess") != null){
            String session_mess = (String) request.getSession().getAttribute("session_mess");
            request.setAttribute(session_mess.split("\\|")[0], session_mess.split("\\|")[1]);
            request.getSession().removeAttribute("session_mess");
        }
        request.setAttribute("floors", arrayList);
        request.setAttribute("buildings", buildings);
        request.getRequestDispatcher("/WEB-INF/views/admin/manage-floors.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int building_id = Integer.parseInt(request.getParameter("building_id"));
        if (new FloorDao().addFloor(building_id)){
            request.getSession().setAttribute("session_mess", "success|Thêm mới tầng thành công!");
        }else {
            request.getSession().setAttribute("session_mess", "error|Thêm mới tầng không thành công!");
        }
        response.sendRedirect(request.getContextPath() + "/admin/manage-floors");
    }
}
