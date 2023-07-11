package Controller.Admin.Room;

import Dao.RoomsDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

public class ServletDeleteRoom extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        if (new RoomsDao().deleteRoom(id)){
            request.getSession().setAttribute("session_mess", "success|Xoá phòng thành công!");
        } else {
            request.getSession().setAttribute("session_mess", "error|Xoá tầng không thành công!");
        }
        response.sendRedirect(request.getContextPath() + "/admin/manage-rooms");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
