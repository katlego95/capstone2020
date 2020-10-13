package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginModel {
    DatabaseConnection connectionNow = new DatabaseConnection();
    Connection connectDB = connectionNow.getConnection();



    public boolean isUser(String user, String phone) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query1 = "SELECT * FROM userdbinfo WHERE email = ?";
        String query2 = "SELECT * FROM userdbinfo WHERE phone = ?";
        try {
            preparedStatement = connectDB.prepareStatement(query1);
            preparedStatement.setString(1,user);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }else {
                try {
                    preparedStatement = connectDB.prepareStatement(query2);
                    preparedStatement.setString(1,phone);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()){
                        return true;
                    }else {
                        return false;
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    return false;
                }finally {
                    preparedStatement.close();
                    resultSet.close();
                }
            }
        }catch (Exception e){
            return false;
        }finally {
            preparedStatement.close();
            resultSet.close();
        }
    }
}
