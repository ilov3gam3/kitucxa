package Controller.Admin.Building;

import Dao.BuildingDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

public class ServletDeleteBuilding extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        if (new BuildingDao().deleteBuilding(id)){
            request.getSession().setAttribute("session_mess", "success|Xoá toà nhà thành công!");
        } else {
            request.getSession().setAttribute("session_mess", "error|Xoá toà nhà không thành công, có thể có nhiều phòng đang ở trong toà nhà này!");
        }
        response.sendRedirect(request.getContextPath() + "/admin/manage-buildings");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
