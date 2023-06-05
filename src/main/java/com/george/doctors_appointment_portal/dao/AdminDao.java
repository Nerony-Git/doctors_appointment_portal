package com.george.doctors_appointment_portal.dao;

import com.george.doctors_appointment_portal.model.Admin;
import com.george.doctors_appointment_portal.model.Doctor;
import com.george.doctors_appointment_portal.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao {

    private UserDao userDao = new UserDao();
    private DoctorDao doctorDao = new DoctorDao();
    private AppointmentDao appointmentDao = new AppointmentDao();
    private SpecialityDao specialityDao = new SpecialityDao();
    private static final String INSERT_ADMIN_SQL = "INSERT INTO admin (userid, first_name, last_name, other_name, dob, email, contact, username, password) VALUES (?,?,?,?,?,?,?,?,?);";
    private static final String LOGIN_ADMIN_SQL = "SELECT * FROM admin WHERE username = ? and password =?";
    private static final String GET_LAST_ADMIN_ID_SQL = "SELECT userid FROM admin ORDER BY userid DESC LIMIT 1";
    private static final String OLD_PASSWORD_SQL = "SELECT * FROM admin WHERE userid = ? and password = ?";
    private static final String UPDATE_PASSWORD_SQL = "UPDATE admin SET password = ? WHERE userid = ?";
    private static final String UPDATE_ADMIN_SQL = "UPDATE admin SET first_name = ?, last_name = ?, other_name = ?, dob = ?, contact = ?, email = ? WHERE userid = ?";
    private static final String ADMIN_BY_ID_SQL = "SELECT * FROM admin WHERE userid = ?";

    public int registerAdmin(Admin admin) throws ClassNotFoundException {

        int result = 0;
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ADMIN_SQL)) {
                String lastUserID = getLastAdminID();
                String newUserID;
                if (lastUserID == null) {
                    newUserID = "ADM0001"; // Default ID if no records exist
                } else {
                    int id = Integer.parseInt(lastUserID.substring(3)) + 1;
                    newUserID = "ADM" + String.format("%04d", id);
                }
                preparedStatement.setString(1, newUserID);
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
        int totalUsers = userDao.totalUsers();
        int totalDoctors = doctorDao.totalDoctors();
        int totalAppointments = appointmentDao.totalAppointments();
        int totalAppointment = appointmentDao.totalAppointment();
        int totalSpecialty = specialityDao.totalSpecialties();

        try (Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_ADMIN_SQL)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                System.out.println(preparedStatement);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    admin = new Admin();
                    admin.setUserID(rs.getString("userid"));
                    admin.setFirstName(rs.getString("first_name"));
                    admin.setLastName(rs.getString("last_name"));
                    admin.setOtherName(rs.getString("other_name"));
                    admin.setDob(rs.getDate("dob").toLocalDate());
                    admin.setEmail(rs.getString("email"));
                    admin.setContact(rs.getString("contact"));
                    admin.setUsername(rs.getString("username"));
                    admin.setTotalUsers(totalUsers);
                    admin.setTotalDoctors(totalDoctors);
                    admin.setTotalAppointments(totalAppointments);
                    admin.setTotalAppointment(totalAppointment);
                    admin.setTotalSpeciality(totalSpecialty);
                }
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        return admin;
    }

    public String getLastAdminID() throws SQLException {
        String lastUserID = null;

        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_LAST_ADMIN_ID_SQL)){
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                lastUserID = resultSet.getString("userid");
            }
        }
        return lastUserID;
    }

    public boolean validateAdminOldPassword(String userID, String oldPassword) throws SQLException {
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

    public boolean changeAdminPassword(String userID, String newPassword) throws SQLException {
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

    public boolean updateAdmin(Admin admin) throws SQLException {
        boolean u;

        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ADMIN_SQL)){
            preparedStatement.setString(1, admin.getFirstName());
            preparedStatement.setString(2, admin.getLastName());
            preparedStatement.setString(3, admin.getOtherName());
            preparedStatement.setDate(4, JDBCUtils.getSQLDate(admin.getDob()));
            preparedStatement.setString(5, admin.getContact());
            preparedStatement.setString(6, admin.getEmail());
            preparedStatement.setString(7, admin.getUserID());
            System.out.println(preparedStatement);
            u = preparedStatement.executeUpdate() > 0;
        }
        return u;
    }

    public Admin getAdminByID(String userID) throws SQLException {
        Admin admin = null;
        int totalUsers = userDao.totalUsers();
        int totalDoctors = doctorDao.totalDoctors();
        int totalAppointments = appointmentDao.totalAppointments();
        int totalAppointment = appointmentDao.totalAppointment();
        int totalSpecialty = specialityDao.totalSpecialties();

        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADMIN_BY_ID_SQL)){
            preparedStatement.setString(1, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                admin = new Admin();

                admin.setUserID(resultSet.getString("userid"));
                admin.setFirstName(resultSet.getString("first_name"));
                admin.setLastName(resultSet.getString("last_name"));
                admin.setOtherName(resultSet.getString("other_name"));
                admin.setUsername(resultSet.getString("username"));
                admin.setDob(resultSet.getDate("dob").toLocalDate());
                admin.setContact(resultSet.getString("contact"));
                admin.setEmail(resultSet.getString("email"));
                admin.setTotalUsers(totalUsers);
                admin.setTotalDoctors(totalDoctors);
                admin.setTotalAppointments(totalAppointments);
                admin.setTotalAppointment(totalAppointment);
                admin.setTotalSpeciality(totalSpecialty);

            }
        }
        return admin;
    }
}
