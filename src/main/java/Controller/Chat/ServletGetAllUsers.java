package Controller.Chat;

import Dao.UserDao;
import Model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ServletGetAllUsers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
//        ArrayList<User> arrayList = new UserDao().getAllUsers();
        int user_id = ((User)req.getSession().getAttribute("user")).id;
        ArrayList<User> arrayList = new UserDao().getAllUsersWithLassMess(user_id);
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i).password = "";
            arrayList.get(i).verify_key = "";
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(arrayList);
        PrintWriter out = resp.getWriter();
        out.print(jsonString);
        out.flush();
    }
}
