package com.george.doctors_appointment_portal.dao;

import com.george.doctors_appointment_portal.model.User;
import com.george.doctors_appointment_portal.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserDao {

    private static final String INSERT_USERS_SQL = "INSERT INTO users (userid, first_name, last_name, other_name, username, dob, contact, address, postal_address, email, password) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
    private static final String LOGIN_SQL = "SELECT * FROM users WHERE username = ? and password = ?";
    private static final String OLD_PASSWORD_SQL = "SELECT * FROM users WHERE userID = ? and password = ?";
    private static final String UPDATE_PASSWORD_SQL = "UPDATE users SET password = ? WHERE userid = ?";
    private static final String UPDATE_USER_SQL = "UPDATE users SET first_name = ?, last_name = ?, other_name = ?, dob = ?, contact = ?, address = ?, postal_address = ?, email = ? WHERE userid = ?";
    private static final String USER_BY_ID = "SELECT * FROM users WHERE userid = ?";

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
                    user.setUserID(rs.getString("userid"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setOtherName(rs.getString("other_name"));
                    user.setUsername(rs.getString("username"));
                    user.setDob(rs.getDate("dob").toLocalDate());
                    user.setContact(rs.getString("contact"));
                    user.setAddress(rs.getString("address"));
                    user.setPostalAddress(rs.getString("postal_address"));
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
        }
        return o;
    }

    public boolean changeUserPassword(String userID, String newPassword) throws SQLException {
        boolean n = false;

        try (Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD_SQL)){
                preparedStatement.setString(1, newPassword);
                preparedStatement.setString(2, userID);

                preparedStatement.executeUpdate();
                n = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;
    }

    public boolean updateUser(User user) throws SQLException {
        boolean u;

        try (Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL)){
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getOtherName());
            preparedStatement.setDate(4, JDBCUtils.getSQLDate(user.getDob()));
            preparedStatement.setString(5, user.getContact());
            preparedStatement.setString(6, user.getAddress());
            preparedStatement.setString(7, user.getPostalAddress());
            preparedStatement.setString(8, user.getEmail());
            preparedStatement.setString(9, user.getUserID());
            System.out.println(preparedStatement);
            u = preparedStatement.executeUpdate() > 0;
        }
        return u;
    }

    public User getUserByID(String userID) throws SQLException {
        User user = null;

        try (Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(USER_BY_ID)){
            preparedStatement.setString(1, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();

                user.setUserID(resultSet.getString("userid"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setOtherName(resultSet.getString("other_name"));
                user.setUsername(resultSet.getString("username"));
                user.setDob(resultSet.getDate("dob").toLocalDate());
                user.setContact(resultSet.getString("contact"));
                user.setAddress(resultSet.getString("address"));
                user.setPostalAddress(resultSet.getString("postal_address"));
                user.setEmail(resultSet.getString("email"));

            }
        }
        return user;
    }

}
