package com.george.doctors_appointment_portal.dao;

import com.george.doctors_appointment_portal.model.Appointment;
import com.george.doctors_appointment_portal.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDao {
    private static final String INSERT_APPOINTMENT_SQL = "INSERT INTO appointment (userID, specialityID, doctorID, appointmentDate, description, status) VALUES (?,?,?,?,?,?)";
    private static final String SELECT_ALL_APPOINTMENT_SQL = "SELECT * FROM appointment";
    private static final String SELECT_USER_APPOINTMENT_SQL = "SELECT * FROM appointment WHERE userID = ?";
    private static final String SELECT_DOCTOR_APPOINTMENT_SQL = "SELECT * FROM appointment WHERE doctorID = ?";
    private static final String SELECT_SPECIALITY_APPOINTMENT_SQL = "SELECT * FROM appointment WHERE specialityID = ?";
    private static final String SELECT_APPOINTMENT_BY_ID_SQL = "SELECT * FROM appointment WHERE sn = ?";
    private static final String DELETE_APPOINTMENT_BY_ID_SQL = "DELETE FROM appointment WHERE sn = ?";
    private static final String UPDATE_APPOINTMENT_BY_ID_SQL = "UPDATE appointment SET userID = ?, specialityID = ?, doctorID = ?, appointmentDate = ?, description = ?, status = ? WHERE sn = ?";

    public void insertAppointment(Appointment appointment) throws SQLException {
        System.out.println(INSERT_APPOINTMENT_SQL);
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_APPOINTMENT_SQL)) {
            preparedStatement.setString(1, appointment.getUserID());
            preparedStatement.setString(2, appointment.getSpecialityID());
            preparedStatement.setString(3, appointment.getDoctorID());
            preparedStatement.setDate(4, JDBCUtils.getSQLDate(appointment.getAppointmentDate()));
            preparedStatement.setString(5, appointment.getDescription());
            preparedStatement.setString(6, appointment.getStatus());

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            JDBCUtils.printSQLException(exception);
        }
    }

    public List<Appointment> selectAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_APPOINTMENT_SQL)) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("sn");
                String userID = rs.getString("userID");
                String specialityID = rs.getString("specialityID");
                String doctorID = rs.getString("doctorID");
                LocalDate appointmentDate = rs.getDate("appointmentDate").toLocalDate();
                String description = rs.getString("description");
                String status = rs.getString("status");
            }
        } catch (SQLException exception) {
            JDBCUtils.printSQLException(exception);
        }
        return appointments;
    }

    public List<Appointment> selectUserAppointments(String user) {
        List<Appointment> userAppointments = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_APPOINTMENT_SQL)) {
            preparedStatement.setString(1, user);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("sn");
                String userID = rs.getString("userID");
                String specialityID = rs.getString("specialityID");
                String doctorID = rs.getString("doctorID");
                LocalDate appointmentDate = rs.getDate("appointmentDate").toLocalDate();
                String description = rs.getString("description");
                String status = rs.getString("status");
            }
        } catch (SQLException exception) {
            JDBCUtils.printSQLException(exception);
        }
        return userAppointments;
    }

    public List<Appointment> selectDoctorAppointments(String doctor) {
        List<Appointment> doctorAppointments = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_DOCTOR_APPOINTMENT_SQL)) {
            preparedStatement.setString(1, doctor);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("sn");
                String userID = rs.getString("userID");
                String specialityID = rs.getString("specialityID");
                String doctorID = rs.getString("doctorID");
                LocalDate appointmentDate = rs.getDate("appointmentDate").toLocalDate();
                String description = rs.getString("description");
                String status = rs.getString("status");
            }
        } catch (SQLException exception) {
            JDBCUtils.printSQLException(exception);
        }
        return doctorAppointments;
    }

    public List<Appointment> selectSpecialityAppointments(String speciality) {
        List<Appointment> specialityAppointments = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SPECIALITY_APPOINTMENT_SQL)) {
            preparedStatement.setString(1, speciality);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("sn");
                String userID = rs.getString("userID");
                String specialityID = rs.getString("specialityID");
                String doctorID = rs.getString("doctorID");
                LocalDate appointmentDate = rs.getDate("appointmentDate").toLocalDate();
                String description = rs.getString("description");
                String status = rs.getString("status");
            }
        } catch (SQLException exception) {
            JDBCUtils.printSQLException(exception);
        }
        return specialityAppointments;
    }

    public List<Appointment> selectAppointment(String aid) {
        List<Appointment> appointmentID = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_APPOINTMENT_BY_ID_SQL)) {
            preparedStatement.setString(1, aid);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("sn");
                String userID = rs.getString("userID");
                String specialityID = rs.getString("specialityID");
                String doctorID = rs.getString("doctorID");
                LocalDate appointmentDate = rs.getDate("appointmentDate").toLocalDate();
                String description = rs.getString("description");
                String status = rs.getString("status");
            }
        } catch (SQLException exception) {
            JDBCUtils.printSQLException(exception);
        }
        return appointmentID;
    }

    public boolean deleteAppointment(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_APPOINTMENT_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateAppointment(Appointment appointment) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_APPOINTMENT_BY_ID_SQL)) {
            preparedStatement.setString(1, appointment.getUserID());
            preparedStatement.setString(2, appointment.getSpecialityID());
            preparedStatement.setString(3, appointment.getDoctorID());
            preparedStatement.setDate(4, JDBCUtils.getSQLDate(appointment.getAppointmentDate()));
            preparedStatement.setString(5, appointment.getDescription());
            preparedStatement.setString(6, appointment.getStatus());
            preparedStatement.setLong(7, appointment.getAppointmentID());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

}
