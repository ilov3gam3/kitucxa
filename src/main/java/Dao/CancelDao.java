package Dao;

import Model.Cancel;
import Model.Status;

import java.sql.*;
import java.util.ArrayList;

public class CancelDao {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    public int addCancel(int bill_id, String reason){
        String sql = "insert into cancels(bill_id, reason, status, created_at) values (?, ?, ?, ?)";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, bill_id);
            preparedStatement.setString(2, reason);
            preparedStatement.setInt(3, Status.PENDING.getValue());
            preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            int row = preparedStatement.executeUpdate();
            if (row > 0){
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                int id;
                if (generatedKeys.next()){
                    id = generatedKeys.getInt(1);
                } else {
                    id = 0;
                }
                return id;
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public ArrayList<Cancel> getAllCancels(){
        String sql = "select cancels.*, \n" +
                "       users.name as username, users.id as user_id,\n" +
                "       bills.start as start, bills.[end] as [end],\n" +
                "       rooms.name as room_name\n" +
                "from cancels \n" +
                "    inner join bills on cancels.bill_id = bills.id \n" +
                "    inner join users on bills.user_id = users.id\n" +
                "    inner join rooms on bills.room_id = rooms.id" +
                " order by cancels.id desc";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            ArrayList<Cancel> arrayList = new ArrayList<>();
            while (resultSet.next()){
                arrayList.add(new Cancel(
                        resultSet.getInt("id"),
                        resultSet.getInt("bill_id"),
                        resultSet.getString("reason"),
                        Status.getByValue(resultSet.getInt("status")),
                        resultSet.getString("created_at"),
                        resultSet.getString("username"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("start"),
                        resultSet.getString("end"),
                        resultSet.getString("room_name"),
                        resultSet.getString("admin_reason")
                ));
            }
            return arrayList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean updateStatus(int cancel_id, int status, int bill_id, String admin_reason){
        if (status == 1){
            String sql = "update cancels set status = ?, admin_reason = ? where id = ?; update bills set status = -1 where id = ?";
            connection = Connect.getConnection();
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, status);
                preparedStatement.setString(2, admin_reason);
                preparedStatement.setInt(3, cancel_id);
                preparedStatement.setInt(4, bill_id);
                int row = preparedStatement.executeUpdate();
                return row > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            String sql = "update cancels set status = ?, admin_reason = ? where id = ?; ";
            connection = Connect.getConnection();
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, status);
                preparedStatement.setString(2, admin_reason);
                preparedStatement.setInt(3, cancel_id);
                int row = preparedStatement.executeUpdate();
                return row > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
    public ArrayList<Cancel> getCancelsOfUser(int user_id){
        String sql = "select cancels.*, users.name as username, users.id as user_id, bills.start as start, bills.[end] as [end], rooms.name as room_name\n" +
                "from cancels\n" +
                "    inner join bills on cancels.bill_id = bills.id\n" +
                "    inner join users on bills.user_id = users.id\n" +
                "    inner join rooms on bills.room_id = rooms.id\n" +
                "where users.id = ? order by cancels.id desc";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user_id);
            resultSet = preparedStatement.executeQuery();
            ArrayList<Cancel> arrayList = new ArrayList<>();
            while (resultSet.next()){
                arrayList.add(new Cancel(
                        resultSet.getInt("id"),
                        resultSet.getInt("bill_id"),
                        resultSet.getString("reason"),
                        Status.getByValue(resultSet.getInt("status")),
                        resultSet.getString("created_at"),
                        resultSet.getString("username"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("start"),
                        resultSet.getString("end"),
                        resultSet.getString("room_name"),
                        resultSet.getString("admin_reason")
                ));
            }
            return arrayList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
