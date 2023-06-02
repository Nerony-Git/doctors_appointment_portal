package com.george.doctors_appointment_portal.dao;

import com.george.doctors_appointment_portal.model.User;
import com.george.doctors_appointment_portal.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserDao {

    private static final String INSERT_USERS_SQL = "INSERT INTO users (userID, firstName, lastName, otherName, username, dob, contact, address, postalAddress, email, password) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
    private static final String LOGIN_SQL = "SELECT * FROM users WHERE username = ? and password = ?";
    private static final String OLD_PASSWORD_SQL = "SELECT * FROM users WHERE userID = ? and password = ?";
    private static final String UPDATE_PASSWORD_SQL = "UPDATE users SET password = ? WHERE userID = ?";
    private static final String UPDATE_USER_SQL = "UPDATE users SET firstName = ?, lastName = ?, otherName = ?, dob = ?, contact = ?, address = ?, postalAddress = ?, email = ? WHERE userID =?";

    public int registerUser(User users) throws ClassNotFoundException {

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

    public boolean validateUserOldPassword(String userID, String oldPassword) throws SQLException {
        boolean o = false;

        try (Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(OLD_PASSWORD_SQL)) {
                preparedStatement.setString(1, userID);
                preparedStatement.setString(2, oldPassword);

                System.out.println(preparedStatement);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    o = true;
                }
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        return o;
    }

}
