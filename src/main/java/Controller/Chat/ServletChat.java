package Controller.Chat;

import Dao.ChatDao;
import Model.Chat;
import Model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ServletChat extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        user.password = "";
        user.verify_key = "";
        ObjectMapper objectMapper = new ObjectMapper();
        String user_json = objectMapper.writeValueAsString(user);
        req.setAttribute("user_json", user_json);
        req.getRequestDispatcher("/WEB-INF/views/chat.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        Gson gson = new Gson();
        Chat payload = gson.fromJson(requestBody.toString(), Chat.class);

        String receiver_id = payload.receiver_id;
        String content = payload.content;
        String sender_id = String.valueOf(((User)req.getSession().getAttribute("user")).id);
        Chat chat = new ChatDao().addChat(sender_id, receiver_id, false, content);
        resp.setContentType("application/json");
        String status;
        if (chat != null){
            String chat_json = objectMapper.writeValueAsString(chat);
            MyWebSocket.broadcastToChannel(chat.sender_id, chat_json);
            MyWebSocket.broadcastToChannel(chat.receiver_id, chat_json);
            status = objectMapper.writeValueAsString(true);
        } else {
            status = objectMapper.writeValueAsString(false);
        }
        PrintWriter out = resp.getWriter();
        out.print(status);
        out.flush();
    }
}
