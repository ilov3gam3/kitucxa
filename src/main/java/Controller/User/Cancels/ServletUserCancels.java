package Controller.User.Cancels;

import Dao.CancelDao;
import Model.Cancel;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

public class ServletUserCancels extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int user_id = ((User) req.getSession().getAttribute("user")).id;
        ArrayList<Cancel> arrayList = new CancelDao().getCancelsOfUser(user_id);
        req.setAttribute("cancels", arrayList);
        req.getRequestDispatcher("/WEB-INF/views/user/cancels.jsp").forward(req, resp);
    }
}
