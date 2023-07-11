package Controller.Admin.Room;

import Dao.RoomsDao;
import Model.Room;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

public class ServletUpdateRoom extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("session_mess")!=null){
            String session = (String) request.getSession().getAttribute("session_mess");
            request.setAttribute(session.split("\\|")[0], session.split("\\|")[1]);
            request.getSession().removeAttribute("session_mess");
        }
        int room_id = Integer.parseInt(request.getParameter("id"));
        RoomsDao roomsDao = new RoomsDao();
        Room room = roomsDao.getRoomById(room_id);
        request.setAttribute("room", room);
        request.getRequestDispatcher("/WEB-INF/views/admin/update-room.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int room_id = Integer.parseInt(request.getParameter("id"));
        boolean is_available = Boolean.parseBoolean(request.getParameter("is_available"));
        int price = Integer.parseInt(request.getParameter("price"));
        RoomsDao roomsDao = new RoomsDao();
        try {
            if (roomsDao.updateRoom(room_id, is_available, price)){
                request.getSession().setAttribute("session_mess", "success|Thay đổi thành công.");
            } else {
                request.getSession().setAttribute("session_mess", "warning|Không có thay đổi nào đc thực hiện");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession().setAttribute("session_mess", "error|Đã có lỗi xảy ra.");
        }
        response.sendRedirect(request.getContextPath() + "/admin/update-room?id=" + room_id);
    }
}
