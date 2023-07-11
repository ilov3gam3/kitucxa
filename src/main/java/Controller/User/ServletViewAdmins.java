package Controller.User;

import Dao.UserDao;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

public class ServletViewAdmins extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        ArrayList<User> admins = userDao.getAdmins();
        req.setAttribute("admins", admins);
        req.getRequestDispatcher("/WEB-INF/views/user/view-admins.jsp").forward(req, resp);
    }
}
