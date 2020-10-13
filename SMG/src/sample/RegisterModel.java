package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.validator.routines.EmailValidator;

public class RegisterModel {
    DatabaseConnection connectionNow = new DatabaseConnection();
    Connection connectDB = connectionNow.getConnection();



    public boolean passwordMatch(String password1,String password2){
        if (password1.equals(password2)) {
            return true;
        }
        else{
            return false;
        }
    }

    public boolean validPhoneNumber(String phoneNumber){
        String regex = "\\d+";
        if (phoneNumber.matches(regex)) {
            return true;
        }
        else{
            return false;
        }
    }

    public boolean validEmail(String email){
        boolean allowLocal;
        allowLocal = true;
        boolean valid = EmailValidator.getInstance(allowLocal).isValid(email);
        return valid;

    }

    public boolean validPassword(String password){
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        if (password.matches(regex)) {
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isRegistered(String name,String email,String phoneNumber,String password, String role) throws SQLException {
        PreparedStatement preparedStatement = null;
        String query2 = "INSERT INTO userdbinfo(name, password, email, phone, role) VALUES(?,?,?,?,?)";
        try{
            preparedStatement = connectDB.prepareStatement(query2);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setString(5,  role);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }finally {
            preparedStatement.close();
        }
    }








}
