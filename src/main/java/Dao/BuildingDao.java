package Dao;

import Model.Building;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BuildingDao {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    public ArrayList<Building> getAllBuilding(){
        ArrayList<Building> arrayList = new ArrayList<>();
        String sql = "select * from buildings";
        try {
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                arrayList.add(new Building(
                    resultSet.getInt("id"),
                    resultSet.getString("name")
                ));
            }
            return arrayList;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public boolean addBuilding(String name){
        String sql = "insert into buildings(name) values (?)";
        try {
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            int row = preparedStatement.executeUpdate();
            return row > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteBuilding(int id){
        String sql = "delete from buildings where id = ?";
        try {
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int row = preparedStatement.executeUpdate();
            return row > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public Building getBuildingById(int id){
        String sql = "select * from buildings where id = ?";
        try {
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                return new Building(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                );
            }
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return null;
    }
    public boolean updateBuilding(int id, String name){
        String sql = "update buildings set name = ? where id = ?";
        try {
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            return row>0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
