package Dao;

import Model.Change;
import Model.Status;

import java.sql.*;
import java.util.ArrayList;

public class ChangeDao {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    public int addChange(int bill_id, String reason, int to_room){
        String sql = "insert into changes(bill_id, reason, room_to_id, status, created_at) values (?, ?, ?, ?, ?)";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,bill_id);
            preparedStatement.setString(2,reason);
            preparedStatement.setInt(3,to_room);
            preparedStatement.setInt(4, Status.PENDING.getValue());
            preparedStatement.setTimestamp(5,new Timestamp(System.currentTimeMillis()));
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
    public ArrayList<Change> getAllChangeRoom(){
        ArrayList<Change> arrayList = new ArrayList<>();
        String sql = "select changes.*,\n" +
                "       bills.user_id as user_id,\n" +
                "       bills.start as start,\n" +
                "       bills.[end] as [end],\n" +
                "       users.name as username,\n" +
                "       r1.name as room_from_name,\n" +
                "       r2.name as room_to_name\n" +
                "from changes\n" +
                "    inner join bills on bills.id = changes.bill_id\n" +
                "    inner join users on bills.user_id = users.id\n" +
                "    inner join rooms r1 on bills.room_id = r1.id\n" +
                "    inner join rooms r2 on changes.room_to_id = r2.id\n" +
                "order by changes.id desc";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                arrayList.add(new Change(
                        resultSet.getInt("id"),
                        resultSet.getInt("bill_id"),
                        resultSet.getString("reason"),
                        resultSet.getInt("room_to_id"),
                        Status.getByValue(resultSet.getInt("status")),
                        resultSet.getString("created_at"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("room_from_name"),
                        resultSet.getString("room_to_name"),
                        resultSet.getString("start"),
                        resultSet.getString("end")
                ));
            }
            return arrayList;
        } catch (SQLException e) {
            e.printStackTrace();
            return arrayList;
        }
    }
    public boolean changeStatus(int id, int status){
        String sql = "update changes set status = ? where id = ?;";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, status);
            preparedStatement.setInt(2,id);
            int row = preparedStatement.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<Change> getChangesOfUsers(int user_id){
        ArrayList<Change> arrayList = new ArrayList<>();
        String sql = "select changes.*,\n" +
                "       bills.user_id as user_id,\n" +
                "       bills.start as start,\n" +
                "       bills.[end] as [end],\n" +
                "       users.name as username,\n" +
                "       r1.name as room_from_name,\n" +
                "       r2.name as room_to_name\n" +
                "from changes\n" +
                "    inner join bills on bills.id = changes.bill_id\n" +
                "    inner join users on bills.user_id = users.id\n" +
                "    inner join rooms r1 on bills.room_id = r1.id\n" +
                "    inner join rooms r2 on changes.room_to_id = r2.id\n" +
                "where users.id = ? order by changes.id desc";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user_id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                arrayList.add(new Change(
                        resultSet.getInt("id"),
                        resultSet.getInt("bill_id"),
                        resultSet.getString("reason"),
                        resultSet.getInt("room_to_id"),
                        Status.getByValue(resultSet.getInt("status")),
                        resultSet.getString("created_at"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("room_from_name"),
                        resultSet.getString("room_to_name"),
                        resultSet.getString("start"),
                        resultSet.getString("end")
                ));
            }
            return arrayList;
        } catch (SQLException e) {
            e.printStackTrace();
            return arrayList;
        }
    }
}
