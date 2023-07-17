package Controller.Chat;

import Dao.ChatDao;
import Dao.UserDao;
import Model.Chat;
import Model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 100,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100  // 100 MB
)
public class ServletUploadFiles extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        ArrayList<User> arrayList = new UserDao().getAdmins();
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
    public String generateUniqueFileName(String originalFileName) {
        // Get the file extension from the original filename
        String extension = "";
        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex >= 0 && dotIndex < originalFileName.length() - 1) {
            extension = originalFileName.substring(dotIndex + 1);
        }

        // Generate a random UUID as the unique part of the filename
        String uniquePart = UUID.randomUUID().toString();

        // Combine the unique part and file extension to create the unique filename
        String uniqueFileName = uniquePart + "." + extension;

        return uniqueFileName;
    }
    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] tokens = contentDisposition.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 1).trim()
                        .replace("\"", "");
            }
        }
        return null;
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("image");
        String fileName = getFileName(filePart);
        assert fileName != null;
        String newFileName = generateUniqueFileName(fileName);
        String uploadDir = req.getServletContext().getRealPath("/") + "mess_uploads";
        Path filePath = Paths.get(uploadDir, newFileName);
        try (InputStream fileContent = filePart.getInputStream()) {
            Files.copy(fileContent, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        String sender_id = String.valueOf(((User)req.getSession().getAttribute("user")).id);
        Chat chat = new ChatDao().addChat(sender_id, req.getParameter("receiver_id"), true, "mess_uploads/" + newFileName);
        resp.setContentType("application/json");
        String status;
        ObjectMapper objectMapper = new ObjectMapper();
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
