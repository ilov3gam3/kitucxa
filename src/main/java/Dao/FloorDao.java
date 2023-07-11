package Dao;

import Model.Floor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FloorDao {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public ArrayList<Floor> getAllFloors() {
        ArrayList<Floor> arrayList = new ArrayList<>();
        String sql = "select * from floors";
        try {
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                arrayList.add(new Floor(
                        resultSet.getInt("id"),
                        resultSet.getInt("floor"),
                        resultSet.getString("name"),
                        resultSet.getInt("building_id")
                ));
            }
            return arrayList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addFloor(int building_id) {
        String sql = "declare @floor int;" +
                "set  @floor = isnull((select top 1 floor from floors where building_id = ? order by id desc), 0);" +
                "declare @building_name nvarchar(50);" +
                "set  @building_name = (select name from buildings  where id = ?);" +
                "insert into floors(floor, name, building_id) values (@floor + 1, @building_name + cast(@floor + 1 as nvarchar), ?);";
        try {
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, building_id);
            preparedStatement.setInt(2, building_id);
            preparedStatement.setInt(3, building_id);
            int row = preparedStatement.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteFloor(int id) {
        String sql = "delete from floors where id = ?";
        try {
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int row = preparedStatement.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Floor getFloorById(int id) {
        String sql = "select * from floors where id = ?";
        try {
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return new Floor(
                        resultSet.getInt("id"),
                        resultSet.getInt("floor"),
                        resultSet.getString("name"),
                        resultSet.getInt("building_id")
                        );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public boolean updateBuilding(int id, int floor, String name, int building_id) {
        String sql = "update floors set floor = ?, name = ?, building_id = ? where id = ?";
        try {
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, floor);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, building_id);
            preparedStatement.setInt(4, id);
            int row = preparedStatement.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<Floor> getAllFloorsWithBuildingName() {
        ArrayList<Floor> arrayList = new ArrayList<>();
        String sql = "select floors.*, buildings.name as building_name from floors inner join buildings on floors.building_id = buildings.id;";
        try {
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                arrayList.add(new Floor(
                        resultSet.getInt("id"),
                        resultSet.getInt("floor"),
                        resultSet.getString("name"),
                        resultSet.getInt("building_id"),
                        resultSet.getString("building_name")
                ));
            }
            return arrayList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
