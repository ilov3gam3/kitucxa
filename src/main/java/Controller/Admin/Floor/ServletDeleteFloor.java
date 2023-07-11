package Controller.Admin.Floor;

import Dao.FloorDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

public class ServletDeleteFloor extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        if (new FloorDao().deleteFloor(id)){
            request.getSession().setAttribute("session_mess", "success|Xoá tầng thành công!");
        } else {
            request.getSession().setAttribute("session_mess", "error|Xoá tầng không thành công, có thể có nhiều phòng đang ở trong tầng này!");
        }
        response.sendRedirect(request.getContextPath() + "/admin/manage-floors");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
