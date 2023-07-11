package Dao;

import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    public int checkUserInfoDuplicate(String student_code, String name, String email){
        String sql = "DECLARE @outputCode INT;\n" +
                "    EXEC CheckDuplicates @stu_code = ?, @mail = ?, @tel = ?, @result_code = @outputCode OUTPUT;\n" +
                "SELECT @outputCode AS result_code;";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, student_code);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            resultSet = preparedStatement.executeQuery();
            int result = -1;
            if (resultSet.next())
                result = resultSet.getInt("result_code");
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean addUser(String student_code, String name, String email, String address, String tel, String dob, String password, String ver_key){
        String sql = "insert into users(student_code, name, email, address, tel, birthday, password,avatar, verify_key, is_admin, is_verified) values (?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?)";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,student_code);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,email);
            preparedStatement.setString(4,address);
            preparedStatement.setString(5,tel);
            preparedStatement.setString(6,dob);
            preparedStatement.setString(7,password);
            preparedStatement.setString(8,"uploads/default-avatar.webp");
            preparedStatement.setString(9,ver_key);
            preparedStatement.setBoolean(10,false);
            preparedStatement.setBoolean(11,false);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public User findUserBy_key(String key){
        try {
            String sql = "select * from users where verify_key = ?";
            connection = Connect.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,key);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return new User(
                        resultSet.getInt("id"),
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
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public boolean activeById(int id){
        String sql = "update users set is_verified = 1, verify_key = '' where id = ?";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            boolean check = preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public User login(String code_or_mail, String password) throws SQLException {
            String sql = "select * from users where student_code = UPPER(?) and password = ? or email = ? and password = ?";
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, code_or_mail);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, code_or_mail);
            preparedStatement.setString(4, password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return new User(
                        resultSet.getInt("id"),
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
                );
            } else {
                return null;
            }
    }
    public boolean updateAvatar(int id, String path){
        try {
            String sql = "update users set avatar = ? where id = ?";
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, path);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            return row>0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean update(String name, String password, int id){
        try {
            String sql = "update users set name = ?, password = ? where id = ?";
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, id);
            int row = preparedStatement.executeUpdate();
            return row>0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<User> getAllUsers(){
        ArrayList<User> arrayList = new ArrayList<>();
        try {
            String sql = "select * from users";
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                arrayList.add(new User(
                    resultSet.getInt("id"),
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
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public boolean updateUUIDByEmail(String email, String uuid){
        String sql = "update users set verify_key = ? where email = ?";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,uuid);
            preparedStatement.setString(2,email);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteKey(String key){
        String sql = "update users set verify_key = '' where verify_key = ?";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,key);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean checkValidKey(String key){
        String sql = "select * from users where verify_key = ?";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, key);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean changePassword(String key, String password){
        String sql = "update users set password = ? where verify_key = ?";
        connection = Connect.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, key);
            int row = preparedStatement.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public User getUserByCode(String code){
        try {
            String sql = "select * from users where student_code = ?";
            connection = Connect.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,code);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return new User(
                        resultSet.getInt("id"),
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
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public User findUserById(int id){
        try {
            String sql = "select * from users where id = ?";
            connection = Connect.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return new User(
                        resultSet.getInt("id"),
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
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public ArrayList<User> getAdmins(){
        ArrayList<User> arrayList = new ArrayList<>();
        try {
            String sql = "select * from users where is_admin = 'true'";
            connection = Connect.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                arrayList.add(new User(
                        resultSet.getInt("id"),
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
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
