package Dao;

import Model.Bill;
import Model.ExtraBill;
import Model.Room;
import Model.Status;

import java.sql.*;
import java.util.ArrayList;

public class BillsDao {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    public boolean updateReview(int bill_id, String review, int rate){
        String sql = "update bills set evaluation = ?, stars = ? where id = ?";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, review);
            preparedStatement.setInt(2, rate);
            preparedStatement.setInt(3, bill_id);
            int row = preparedStatement.executeUpdate();
            return row> 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public int registerARoom(int user_id, String[] time, int room_id){
        String sql = "insert into bills(room_id, user_id, price, status, evaluation, stars, start, [end], created_at) values(?, ?, (select price from rooms where id = ?), 0, '', -1, ?, ?, ?)";
        try {
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, room_id);
            preparedStatement.setInt(2, user_id);
            preparedStatement.setInt(3, room_id);
            preparedStatement.setString(4, time[0]);
            preparedStatement.setString(5, time[1]);
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
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
        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    public int moveToRoom(int user_id, String[] time, int room_id){
        String sql = "insert into bills(room_id, user_id, price, status, evaluation, stars, start, [end], created_at) values(?, ?, (select price from rooms where id = ?), 1, '', -1, ?, ?, ?)";
        try {
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, room_id);
            preparedStatement.setInt(2, user_id);
            preparedStatement.setInt(3, room_id);
            preparedStatement.setString(4, time[0]);
            preparedStatement.setString(5, time[1]);
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
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
        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    public ArrayList<Bill> getAllBillsOfUser(int user_id){
        ArrayList<Bill> arrayList = new ArrayList<>();
        String sql = "select bills.*,\n" +
                "       cancels.id as cancel_id, cancels.status as cancel_status, cancels.reason as cancel_reason, cancels.created_at as cancel_created_at,\n" +
                "       rooms.name as room_name,\n" +
                "       c.id as change_id, c.reason as change_reason, c.room_to_id as change_room_to_id, c.status as change_status  ,c.created_at as change_created_at\n" +
                "from bills\n" +
                "    inner join rooms on bills.room_id = rooms.id\n" +
                "    left join cancels on cancels.bill_id = bills.id\n" +
                "    left join (select top 1 changes.* from changes group by bill_id, id, reason, room_to_id, status, created_at, admin_reason order by id desc ) as c on bills.id = c.bill_id\n" +
                "where bills.user_id = ? order by bills.id desc";
        try {
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user_id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                arrayList.add(new Bill(
                    resultSet.getInt("id"),
                    resultSet.getInt("room_id"),
                    resultSet.getInt("user_id"),
                    resultSet.getInt("price"),
                    Status.getByValue(resultSet.getInt("status")),
                    resultSet.getString("evaluation"),
                    resultSet.getInt("stars"),
                    resultSet.getString("start"),
                    resultSet.getString("end"),
                    resultSet.getString("created_at"),
                    resultSet.getString("room_name"),
                    resultSet.getInt("cancel_id"),
                    resultSet.getString("cancel_reason"),
                    Status.getByValue(resultSet.getInt("cancel_status")),
                    resultSet.getString("cancel_created_at"),
                    resultSet.getInt("change_id"),
                    resultSet.getString("change_reason"),
                    resultSet.getInt("change_room_to_id"),
                    Status.getByValue(resultSet.getInt("change_status")),
                    resultSet.getString("change_created_at")
                ));
            }
            return arrayList;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<Bill> getAllBills(){
        ArrayList<Bill> arrayList = new ArrayList<>();
        String sql = "select bills.*, rooms.name as room_name, users.student_code as student_code from bills inner join rooms on bills.room_id = rooms.id inner join users on bills.user_id = users.id order by bills.id desc ;";
        try {
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                arrayList.add(new Bill(
                        resultSet.getInt("id"),
                        resultSet.getInt("room_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("price"),
                        Status.getByValue(resultSet.getInt("status")),
                        resultSet.getString("evaluation"),
                        resultSet.getInt("stars"),
                        resultSet.getString("start"),
                        resultSet.getString("end"),
                        resultSet.getString("created_at"),
                        resultSet.getString("room_name"),
                        resultSet.getString("student_code")
                ));
            }
            return arrayList;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean changeStatus(int bill_id, Status status){
        String sql = "update bills set status = ? where id = ?";
        try {
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, status.getValue());
            preparedStatement.setInt(2, bill_id);
            int row = preparedStatement.executeUpdate();
            return row > 0;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public int checkUserRegisterRoom(int user_id, String[] semester){
        String sql = "DECLARE @Result INT\n" +
                "exec checkUserRegisterRoom @user_id = ?, @start_date = ?, @end_date = ? ,  @record = @Result OUTPUT\n" +
                "select @Result as numOfRecord";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user_id);
            preparedStatement.setString(2, semester[0]);
            preparedStatement.setString(3, semester[1]);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return 1;
            } else {
                return 0;
            }
        } catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }
    public Bill getBillById(int bill_id){
        String sql = "select bills.*, rooms.name as room_name, users.student_code as student_code from bills inner join rooms on bills.room_id = rooms.id inner join users on bills.user_id = users.id where bills.id = ? order by bills.id desc;";
        try {
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bill_id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return new Bill(
                        resultSet.getInt("id"),
                        resultSet.getInt("room_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("price"),
                        Status.getByValue(resultSet.getInt("status")),
                        resultSet.getString("evaluation"),
                        resultSet.getInt("stars"),
                        resultSet.getString("start"),
                        resultSet.getString("end"),
                        resultSet.getString("created_at"),
                        resultSet.getString("room_name"),
                        resultSet.getString("student_code")
                );
            } else {
                return null;
            }
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<ExtraBill> getRoomsInSemester(String[] semester){
        ArrayList<ExtraBill> arrayList = new ArrayList<>();
        String sql = "select bills.room_id, rooms.name as room_name, bills.start as start, bills.[end] as [end], bills.price, extra_bills.*\n" +
                "from bills\n" +
                "    inner join rooms on bills.room_id = rooms.id\n" +
                "    left join extra_bills on rooms.id = extra_bills.room_id and extra_bills.start = ? and extra_bills.[end] = ?\n" +
                "where bills.status = 1 and bills.start = ? and bills.[end] = ?\n" +
                "group by bills.room_id, rooms.name, bills.start, bills.[end], bills.price, extra_bills.id, electricity, electricity_price, water, water_price, extra_bills.status, extra_bills.room_id, extra_bills.start, extra_bills.[end], extra_bills.electricity_end, extra_bills.water_end";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, semester[0]);
            preparedStatement.setString(2, semester[1]);
            preparedStatement.setString(3, semester[0]);
            preparedStatement.setString(4, semester[1]);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                arrayList.add(new ExtraBill(
                    resultSet.getInt(6),
                    resultSet.getInt(7),
                    resultSet.getInt(8),
                    resultSet.getInt(9),
                    resultSet.getInt(10),
                    resultSet.getBoolean(11),
                    resultSet.getInt(1),
                    resultSet.getString(13),
                    resultSet.getString(14),
                    resultSet.getString(2),
                    resultSet.getInt(5),
                    resultSet.getInt(15),
                    resultSet.getInt(16)
                ));
            }
            return arrayList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<Integer> getBillIdBy(String room_id, String start, String end){
        ArrayList<Integer> arrayList = new ArrayList<>();
        String sql = "select id from bills where room_id = ? and start = ? and [end] = ? and status = 1\n";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, room_id);
            preparedStatement.setString(2, start);
            preparedStatement.setString(3, end);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                arrayList.add(resultSet.getInt(1));
            }
            return arrayList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
