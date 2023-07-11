package Controller.Admin.User;

import Dao.UserDao;
import Model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;

public class ServletUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<User> arrayList = new UserDao().getAllUsers();
        request.setAttribute("users", arrayList);
        request.getRequestDispatcher("/WEB-INF/views/admin/manage-users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
