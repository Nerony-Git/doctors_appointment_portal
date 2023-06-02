package com.george.doctors_appointment_portal.dao;

import com.george.doctors_appointment_portal.model.User;
import com.george.doctors_appointment_portal.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserDao {
    public int registerUser(User users) throws ClassNotFoundException {
        String INSERT_USERS_SQL = "INSERT INTO users (userID, firstName, lastName, otherName, username, dob, contact, address, postalAddress, email, password) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
        int result = 0;
        try (Connection connection = JDBCUtils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, users.getUserID());
            preparedStatement.setString(2, users.getFirstName());
            preparedStatement.setString(3, users.getLastName());
            preparedStatement.setString(4, users.getOtherName());
            preparedStatement.setString(5, users.getUsername());
            preparedStatement.setDate(6, JDBCUtils.getSQLDate(users.getDob()));
            preparedStatement.setString(7, users.getContact());
            preparedStatement.setString(8, users.getAddress());
            preparedStatement.setString(9, users.getPostalAddress());
            preparedStatement.setString(10, users.getEmail());
            preparedStatement.setString(11, users.getPassword());

            System.out.println(preparedStatement);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        return result;
    }

    public User validateUser(String username, String password) throws SQLException, ClassNotFoundException{
        User user = null;
        String LOGIN_SQL = "SELECT * FROM users WHERE username = ? and password =?";

        Class.forName("org.postgresql.ds.PGConnectionPoolDataSource");
        try (Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_SQL)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                System.out.println(preparedStatement);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    user = new User();
                    user.setUserID(rs.getString("userID"));
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    user.setOtherName(rs.getString("otherName"));
                    user.setUsername(rs.getString("username"));
                    user.setDob(rs.getDate("dob").toLocalDate());
                    user.setContact(rs.getString("contact"));
                    user.setAddress(rs.getString("address"));
                    user.setPostalAddress(rs.getString("postalAddress"));
                    user.setEmail(rs.getString("email"));
                }
            } catch (SQLException e) {
                JDBCUtils.printSQLException(e);
        }
        return user;
    }
}
