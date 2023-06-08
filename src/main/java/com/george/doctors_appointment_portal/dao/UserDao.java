package com.george.doctors_appointment_portal.dao;

import com.george.doctors_appointment_portal.model.Doctor;
import com.george.doctors_appointment_portal.model.User;
import com.george.doctors_appointment_portal.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private static final String INSERT_USERS_SQL = "INSERT INTO users (userid, first_name, last_name, other_name, username, dob, contact, address, postal_address, email, password) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
    private static final String LOGIN_SQL = "SELECT * FROM users WHERE username = ? and password = ?";
    private static final String OLD_PASSWORD_SQL = "SELECT * FROM users WHERE userID = ? and password = ?";
    private static final String UPDATE_PASSWORD_SQL = "UPDATE users SET password = ? WHERE userid = ?";
    private static final String UPDATE_USER_SQL = "UPDATE users SET first_name = ?, last_name = ?, other_name = ?, dob = ?, contact = ?, address = ?, postal_address = ?, email = ? WHERE userid = ?";
    private static final String USER_BY_ID = "SELECT * FROM users WHERE userid = ?";
    private static final String GET_USERS_NAME = "SELECT first_name, last_name, other_name FROM users WHERE userid = ?";
    private static final String GET_LAST_USER_ID = "SELECT userid FROM users ORDER BY userid DESC LIMIT 1";
    private static final String COUNT_ALL_USERS_SQL = "SELECT COUNT(*) AS user_count FROM users";
    private static final String SELECT_ALL_USERS_SQL = "SELECT * FROM users ORDER BY sn ASC";
    private static final String DELETE_USER_BY_ID_SQL = "DELETE FROM users WHERE userid = ?";

    public int registerUser(User users) throws ClassNotFoundException {

        int result = 0;
        try (Connection connection = JDBCUtils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {

            String lastUserID = getLastUserID();
            String newUserID;
            if (lastUserID == null) {
                newUserID = "CUS0001"; // Default ID if no records exist
            } else {
                int id = Integer.parseInt(lastUserID.substring(3)) + 1;
                newUserID = "CUS" + String.format("%04d", id);
            }

            preparedStatement.setString(1, newUserID);
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

    public String getUsersName(String id) throws SQLException {
        String usersName = null;

        try (Connection connection = JDBCUtils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_USERS_NAME)){
            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String otherName = resultSet.getString("other_name");

                usersName = firstName + " " + otherName + " " + lastName;
            }

        }
        return usersName;
    }

    public String getLastUserID() throws SQLException {
        String lastUserID = null;

        try (Connection connection = JDBCUtils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_LAST_USER_ID)){
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                lastUserID = resultSet.getString("userid");
            }
        }
        return lastUserID;
    }

    public int totalUsers() throws SQLException{
        int totalUsers = 0;

        try (Connection connection = JDBCUtils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(COUNT_ALL_USERS_SQL)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                totalUsers = resultSet.getInt("user_count");
            }
        }
        return totalUsers;
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> allUser = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS_SQL)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String userID = resultSet.getString("userid");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String otherName = resultSet.getString("other_name");
                String username = resultSet.getString("username");
                LocalDate dob = resultSet.getDate("dob").toLocalDate();
                String contact = resultSet.getString("contact");
                String address = resultSet.getString("address");
                String postalAddress = resultSet.getString("postal_address");
                String email = resultSet.getString("email");

                allUser.add(new User(userID, firstName, lastName, otherName, username, dob, contact, address, postalAddress, email));

            }
        }
        return allUser;
    }

    public boolean deleteUser(String userID) throws SQLException {
        boolean d;
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_ID_SQL)){
            preparedStatement.setString(1, userID);

            d = preparedStatement.executeUpdate() > 0;
        }
        return d;
    }

}
