package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExtraBillDao {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    public boolean create(String room_id, String start, String end, String electricity, String electricity_price, String water, String water_price, boolean status){
        String sql = "insert into extra_bills(electricity, electricity_price, water, water_price, status, room_id, start, [end])\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
            int row = preparedStatement.executeUpdate();
            return row>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean update(String id, String electricity, String electricity_price, String water, String water_price, boolean status){
        String sql = "update extra_bills set electricity = ?, electricity_price = ?, water = ?, water_price = ?, status = ? where id = ?";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, electricity);
            preparedStatement.setString(2, electricity_price);
            preparedStatement.setString(3, water);
            preparedStatement.setString(4, water_price);
            preparedStatement.setBoolean(5, status);
            preparedStatement.setString(6, id);
            int row = preparedStatement.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
