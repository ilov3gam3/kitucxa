package Controller.Chat;

import Dao.ChatDao;
import Model.Chat;
import Model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ServletGetMessWith extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user_id_chat_with = req.getParameter("user_id_chat_with");
        ChatDao chatDao = new ChatDao();
        String login_user = String.valueOf(((User)req.getSession().getAttribute("user")).id);
        ArrayList<Chat> arrayList = chatDao.getChatWith(login_user, user_id_chat_with);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(arrayList);
        PrintWriter out = resp.getWriter();
        out.print(jsonString);
        out.flush();
    }
}
