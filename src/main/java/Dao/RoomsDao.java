package Dao;

import Model.Room;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomsDao {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    public Room get1RoomWithNumbers(int id, String[] semesters){
        String sql = "select rooms.*, count(bills.user_id) as number from rooms inner join floors on floors.id = rooms.floor_id left join bills on rooms.id = bills.room_id AND (bills.start >= ? AND bills.[end] <= ? AND bills.status = 1) where rooms.id = ? group by rooms.id, rooms.name, floor_id, is_available, rooms.price;";
        try{
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, semesters[0]);
            preparedStatement.setString(2, semesters[1]);
            preparedStatement.setInt(3, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return new Room(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("floor_id"),
                        resultSet.getBoolean("is_available"),
                        resultSet.getInt("price"),
                        resultSet.getInt("number")
                );
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }
    /*public ArrayList<Room> getAllRooms(){
        ArrayList<Room> arrayList = new ArrayList<>();
        try{
            String sql = "select rooms.*, floors.name as floor_name, from rooms inner join floors on floors.id = rooms.floor_id;";
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                arrayList.add(new Room(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("floor_id"),
                        resultSet.getBoolean("is_available"),
                        resultSet.getInt("price"),
                        resultSet.getInt("price"),
                        ));
            }
            return arrayList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }*/
    public ArrayList<Room> getAllRoomsWithNumbers(String[] semesters){
        ArrayList<Room> arrayList = new ArrayList<>();
        try{
            String sql = "select rooms.*, count(bills.user_id) as number from rooms left join bills on rooms.id = bills.room_id AND (bills.start >= ? AND bills.[end] <= ? AND bills.status = 1) group by rooms.id, rooms.name, floor_id, is_available, rooms.price; select * from floors; select * from buildings;";
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, semesters[0]);
            preparedStatement.setString(2, semesters[1]);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                arrayList.add(new Room(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("floor_id"),
                        resultSet.getBoolean("is_available"),
                        resultSet.getInt("price"),
                        resultSet.getInt("number")
                ));
            }
            return arrayList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public boolean createRoom(int floor_id, boolean is_available, int price){
        try{
            String sql = "declare @room_name nvarchar(50)\n" +
                    "declare @floor_id int;" +
                    "SET @floor_id = ?;" +
                    "SET @room_name = isnull((select top 1 name from rooms where floor_id = @floor_id order by id desc), CONCAT((select floors.name from floors where floors.id = @floor_id), N'00'));\n" +
                    "SET @room_name = CONCAT(SUBSTRING(@room_name, 1, LEN(@room_name) - 1), CAST(RIGHT(@room_name, 1) + 1 AS NVARCHAR(1)));\n" +
                    "SELECT @room_name;" +
                    "insert into rooms(name, floor_id, is_available, price) values (@room_name, @floor_id, ?, ?);";
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, floor_id);
            preparedStatement.setBoolean(2, is_available);
            preparedStatement.setInt(3, price);
            boolean check = preparedStatement.execute();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteRoom(int id){
        try{
            String sql = "delete from rooms where id = ?";
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int rowAffected = preparedStatement.executeUpdate();
            return rowAffected > 0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
//    public Room getRoom(int id){
//        try{
//            String sql = "select * from rooms where id = ?";
//            connection = Connect.getConnection();
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, id);
//            resultSet = preparedStatement.executeQuery();
//            Room room = null;
//            if (resultSet.next()){
//                room = new Room(
//                        resultSet.getInt("id"),
//                        resultSet.getString("name"),
//                        resultSet.getInt("floor"),
//                        resultSet.getString("building"),
//                        resultSet.getBoolean("is_available"),
//                        resultSet.getInt("price")
//                );
//            }
//            return room;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
    public boolean updateRoom(int id, boolean is_available, int price) throws SQLException{
            String sql = "update rooms set is_available = ?, price = ? where id = ?";
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, is_available);
            preparedStatement.setInt(2, price);
            preparedStatement.setInt(3, id);
            int rowAffected = preparedStatement.executeUpdate();
            return rowAffected > 0;
    }
    public ArrayList<User> getUsersInRoom(int room_id, String start_date, String end_date){
        ArrayList<User> arrayList = new ArrayList<>();
        String sql = "select * from bills inner join users u on u.id = bills.user_id where start = ? and [end] = ? and room_id = ? and status = 1;";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, start_date);
            preparedStatement.setString(2, end_date);
            preparedStatement.setInt(3, room_id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                arrayList.add(new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("student_code"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("address"),
                        resultSet.getString("tel"),
                        resultSet.getString("birthday"),
                        resultSet.getString("password"),
                        resultSet.getString("avatar"),
                        resultSet.getString("verify_key"),
                        resultSet.getBoolean("is_admin"),
                        resultSet.getBoolean("is_verified")
                ));
            }
            return arrayList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Room getRoomById(int id){
        try{
            String sql = "select * from rooms where id = ?";
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            Room room = null;
            if (resultSet.next()){
                room = new Room(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("floor_id"),
                        resultSet.getBoolean("is_available"),
                        resultSet.getInt("price")
                );
            }
            return room;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
