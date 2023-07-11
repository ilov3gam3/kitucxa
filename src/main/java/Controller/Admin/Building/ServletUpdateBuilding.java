package Controller.Admin.Building;

import Dao.BuildingDao;
import Model.Building;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

public class ServletUpdateBuilding extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Building building = new BuildingDao().getBuildingById(id);
        request.setAttribute("building", building);
        request.getRequestDispatcher("/WEB-INF/views/admin/update-building.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        int id = Integer.parseInt(request.getParameter("id"));
        if (new BuildingDao().updateBuilding(id, name)){
            request.getSession().setAttribute("session_mess", "success|Cập nhật toà nhà thành công!");
        }else {
            request.getSession().setAttribute("session_mess", "error|Cập nhật toà nhà không thành công!");
        }
        response.sendRedirect(request.getContextPath() + "/admin/manage-buildings");
    }
}
