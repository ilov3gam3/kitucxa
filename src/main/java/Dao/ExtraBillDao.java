package Dao;

import Model.ExtraBill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExtraBillDao {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    public boolean create(String room_id, String start, String end, String electricity, String electricity_price, String water, String water_price, boolean status, String electricity_end, String water_end){
        String sql = "insert into extra_bills(electricity, electricity_price, water, water_price, status, room_id, start, [end], electricity_end , water_end )\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,electricity);
            preparedStatement.setString(2, electricity_price);
            preparedStatement.setString(3, water);
            preparedStatement.setString(4, water_price);
            preparedStatement.setBoolean(5, status);
            preparedStatement.setString(6, room_id);
            preparedStatement.setString(7, start);
            preparedStatement.setString(8,end);
            preparedStatement.setString(9,electricity_end);
            preparedStatement.setString(10,water_end);
            int row = preparedStatement.executeUpdate();
            return row>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean update(String id, String electricity, String electricity_price, String water, String water_price, boolean status, String electricity_end, String water_end){
        String sql = "update extra_bills set electricity = ?, electricity_price = ?, water = ?, water_price = ?, status = ?, electricity_end = ?, water_end = ? where id = ?";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, electricity);
            preparedStatement.setString(2, electricity_price);
            preparedStatement.setString(3, water);
            preparedStatement.setString(4, water_price);
            preparedStatement.setBoolean(5, status);
            preparedStatement.setString(6, electricity_end);
            preparedStatement.setString(7, water_end);
            preparedStatement.setString(8, id);
            int row = preparedStatement.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ExtraBill> getExtraWithUsername(int extra_bill_id){
        ArrayList<ExtraBill> arrayList = new ArrayList<>();
        String sql = "select bills.id as bill_id, users.name as username,users.email as user_email, rooms.name as room_name,extra_bills.*\n" +
                "from bills\n" +
                "    inner join extra_bills on extra_bills.start = bills.start and extra_bills.room_id = bills.room_id\n" +
                "    inner join users on bills.user_id = users.id\n" +
                "    inner join rooms on bills.room_id = rooms.id\n" +
                "where extra_bills.id = ? and bills.status = 1;";
        connection= Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, extra_bill_id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ExtraBill extraBill = new ExtraBill();
                extraBill.ExtraBillForSendMail(
                        resultSet.getInt("id"),
                        resultSet.getInt("electricity"),
                        resultSet.getInt("electricity_price"),
                        resultSet.getInt("water"),
                        resultSet.getInt("water_price"),
                        resultSet.getBoolean("status"),
                        resultSet.getInt("room_id"),
                        resultSet.getString("start"),
                        resultSet.getString("end"),
                        resultSet.getString("room_name"),
                        resultSet.getInt("bill_id"),
                        resultSet.getString("username"),
                        resultSet.getString("user_email"),
                        resultSet.getInt("electricity_end"),
                        resultSet.getInt("water_end")
                );
                arrayList.add(extraBill);
            }
            return arrayList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

}
