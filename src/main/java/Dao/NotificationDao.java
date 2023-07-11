package Dao;

import Model.Data;
import Model.Notification;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NotificationDao {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    public static String convertToSQLDateTime(String datetimeLocalString) {
        try {
            // Parse the datetime-local string into a Date object
            SimpleDateFormat datetimeLocalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date datetime = datetimeLocalFormat.parse(datetimeLocalString);

            // Format the Date object into a SQL-compatible string
            SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sqlDateString = sqlDateFormat.format(datetime);

            return sqlDateString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean addNotification(String name, String content, String start, String end){
        String startTime = convertToSQLDateTime(start);
        String endTime = convertToSQLDateTime(end);
        String sql = "insert into notifications(name, content, start, [end], created_at) values (?,?,?,?,?)";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, content);
            preparedStatement.setString(3, startTime);
            preparedStatement.setString(4, endTime);
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            int row = preparedStatement.executeUpdate();
            return row>0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<Notification> getAllNotifications(){
        ArrayList<Notification> notifications = new ArrayList<>();
        String sql = "select * from notifications order by id desc ";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                notifications.add(new Notification(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("content"),
                        resultSet.getString("start"),
                        resultSet.getString("end"),
                        resultSet.getString("created_at")
                ));
            }
            return notifications;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean deleteNotification(int id){
        String sql = "delete from notifications where id = ?";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int row = preparedStatement.executeUpdate();
            return row>0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public Notification getNotificationById(int id){
        String sql = "select * from notifications where id = ?";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return new Notification(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("content"),
                        resultSet.getString("start"),
                        resultSet.getString("end"),
                        resultSet.getString("created_at")
                );
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean updateNotification(int id, String name, String content, String start, String end){
        String startTime = convertToSQLDateTime(start);
        String endTime = convertToSQLDateTime(end);
        String sql = "update notifications set name = ?, content = ?, start = ?, [end] = ? where id = ?";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, content);
            preparedStatement.setString(3, startTime);
            preparedStatement.setString(4, endTime);
            preparedStatement.setInt(5, id);
            int row = preparedStatement.executeUpdate();
            return row>0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public Data getCurrentNotifications(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = new Date();
        String formattedTime = dateFormat.format(currentTime);
        ArrayList<Notification> notifications = new ArrayList<>();
        ArrayList<Notification> end_notifications = new ArrayList<>();
        Data data = new Data();
        String sql = "select * from notifications where start <= ? and [end] >= ? order by id desc ; select top 5 * from notifications where [end] < ? order by id desc;";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, formattedTime);
            preparedStatement.setString(2, formattedTime);
            preparedStatement.setString(3, formattedTime);
            boolean check = preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            if (check){
                while (resultSet.next()){
                    notifications.add(new Notification(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("content"),
                            resultSet.getString("start"),
                            resultSet.getString("end"),
                            resultSet.getString("created_at")
                    ));
                }
                if (preparedStatement.getMoreResults()){
                    resultSet = preparedStatement.getResultSet();
                    while (resultSet.next()){
                        end_notifications.add(new Notification(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("content"),
                                resultSet.getString("start"),
                                resultSet.getString("end"),
                                resultSet.getString("created_at")
                        ));
                    }
                }
            }
            data.currentNotifications = notifications;
            data.top5Notification = end_notifications;
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
