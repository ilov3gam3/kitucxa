package Controller.User.ChangeRoom;

import Dao.ChangeDao;
import Model.Change;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

public class ServletViewChangeRooms extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int user_id = ((User) req.getSession().getAttribute("user")).id;
        ArrayList<Change> changes = new ChangeDao().getChangesOfUsers(user_id);
        req.setAttribute("changes", changes);
        req.getRequestDispatcher("/WEB-INF/views/user/view-changes.jsp").forward(req, resp);
    }
}
