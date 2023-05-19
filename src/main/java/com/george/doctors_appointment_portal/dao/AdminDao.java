package com.george.doctors_appointment_portal.dao;

import com.george.doctors_appointment_portal.model.Admin;
import com.george.doctors_appointment_portal.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao {
    public int registerAdmin(Admin admin) throws ClassNotFoundException {
        String INSERT_ADMIN_SQL = "INSERT INTO admin (userID, firstName, lastName, otherName, dob, email, contact, username, password) VALUES (?,?,?,?,?,?,?,?,?);";
        int result = 0;
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ADMIN_SQL)) {
                preparedStatement.setString(1, admin.getUserID());
                preparedStatement.setString(2, admin.getFirstName());
                preparedStatement.setString(3, admin.getLastName());
                preparedStatement.setString(4, admin.getOtherName());
                preparedStatement.setString(5, admin.getDob());
                preparedStatement.setString(6, admin.getEmail());
                preparedStatement.setString(7, admin.getContact());
                preparedStatement.setString(8, admin.getUsername());
                preparedStatement.setString(9, admin.getPassword());

                System.out.println(preparedStatement);
                result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        return result;
    }

    public Admin validateAdmin(String username, String password) throws SQLException, ClassNotFoundException {
        Admin admin = null;
        String LOGIN_ADMIN_SQL = "SELECT * FROM admin WHERE username = ? and password =?";

        Class.forName("org.postgresql.ds.PGConnectPoolDataSource");
        try (Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_ADMIN_SQL)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                System.out.println(preparedStatement);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    admin = new Admin();
                    admin.setUsername(rs.getString("username"));
                    admin.setFirstName(rs.getString("firstName"));
                }
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        return admin;
    }
}
