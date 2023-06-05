package com.george.doctors_appointment_portal.dao;

import com.george.doctors_appointment_portal.model.Admin;
import com.george.doctors_appointment_portal.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao {

    private static final String INSERT_ADMIN_SQL = "INSERT INTO admin (userid, first_name, last_name, other_name, dob, email, contact, username, password) VALUES (?,?,?,?,?,?,?,?,?);";
    private static final String LOGIN_ADMIN_SQL = "SELECT * FROM admin WHERE username = ? and password =?";
    public int registerAdmin(Admin admin) throws ClassNotFoundException {

        int result = 0;
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ADMIN_SQL)) {
                preparedStatement.setString(1, admin.getUserID());
                preparedStatement.setString(2, admin.getFirstName());
                preparedStatement.setString(3, admin.getLastName());
                preparedStatement.setString(4, admin.getOtherName());
                preparedStatement.setDate(5, JDBCUtils.getSQLDate(admin.getDob()));
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

        Class.forName("org.postgresql.ds.PGConnectPoolDataSource");
        try (Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_ADMIN_SQL)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                System.out.println(preparedStatement);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    admin = new Admin();
                    admin.setUsername(rs.getString("userid"));
                    admin.setFirstName(rs.getString("first_name"));
                    admin.setLastName(rs.getString("last_name"));
                    admin.setOtherName(rs.getString("other_name"));
                    admin.setDob(rs.getDate("dob").toLocalDate());
                    admin.setEmail(rs.getString("email"));
                    admin.setContact(rs.getString("contact"));
                    admin.setUsername(rs.getString("username"));
                }
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        return admin;
    }
}
