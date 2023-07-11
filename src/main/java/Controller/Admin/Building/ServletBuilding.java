package Controller.Admin.Building;

import Dao.BuildingDao;
import Model.Building;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;

public class ServletBuilding extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Building> arrayList = new BuildingDao().getAllBuilding();
        if (request.getSession().getAttribute("session_mess") != null){
            String session_mess = (String) request.getSession().getAttribute("session_mess");
            request.setAttribute(session_mess.split("\\|")[0], session_mess.split("\\|")[1]);
            request.getSession().removeAttribute("session_mess");
        }
        request.setAttribute("buildings", arrayList);
        request.getRequestDispatcher("/WEB-INF/views/admin/manage-buildings.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        if (new BuildingDao().addBuilding(name)){
            request.getSession().setAttribute("session_mess", "success|Thêm mới toà nhà thành công!");
        } else {
            request.getSession().setAttribute("session_mess", "error|Thêm mới toà nhà không thành công!");
        }
        response.sendRedirect(request.getContextPath() + "/admin/manage-buildings");
    }
}
