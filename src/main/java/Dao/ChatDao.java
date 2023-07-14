package Dao;

import Model.Chat;

import java.sql.*;

public class ChatDao {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public Chat addChat(String sender_id, String receiver_id, boolean is_image, String content) {
        String sql = "insert into chats(sender_id, receiver_id, is_image, content, created_at) VALUES (?, ?, ?, ?, ?);SELECT * FROM chats WHERE ID = (SELECT IDENT_CURRENT('chats'))";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, sender_id);
            preparedStatement.setString(2, receiver_id);
            preparedStatement.setBoolean(3, is_image);
            preparedStatement.setString(4, content);
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return new Chat(
                        resultSet.getString("id"),
                        resultSet.getString("sender_id"),
                        resultSet.getString("receiver_id"),
                        resultSet.getBoolean("is_image"),
                        resultSet.getString("content"),
                        resultSet.getString("created_at")

                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) {
        System.out.println(new ChatDao().addChat("2", "3", true, "123123123"));
    }
}
