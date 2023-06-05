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
    private static final String INSERT_APPOINTMENT_SQL = "INSERT INTO appointments (userid, speciality_id, appointment_date, description, status) VALUES (?,?,?,?,?)";
    private static final String SELECT_ALL_APPOINTMENT_SQL = "SELECT * FROM appointments";
    private static final String SELECT_USER_APPOINTMENT_SQL = "SELECT * FROM appointments WHERE userid = ?";
    private static final String SELECT_DOCTOR_APPOINTMENT_SQL = "SELECT * FROM appointments WHERE doctor_id = ?";
    private static final String SELECT_DOCTOR_APPOINTMENTS_SQL = "SELECT * FROM appointments WHERE doctor_id = ? AND status = ?";
    private static final String SELECT_SPECIALITY_APPOINTMENT_SQL = "SELECT * FROM appointments WHERE speciality_id = ?";
    private static final String SELECT_APPOINTMENT_BY_ID_SQL = "SELECT * FROM appointments WHERE sn = ?";
    private static final String DELETE_APPOINTMENT_BY_ID_SQL = "DELETE FROM appointments WHERE sn = ?";
    private static final String UPDATE_APPOINTMENT_BY_ID_SQL = "UPDATE appointments SET userid = ?, speciality_id = ?, doctor_id = ?, appointment_date = ?, description = ?, status = ?, response = ? WHERE sn = ?";
    private static final String UPDATE_APPOINTMENT_BY_DOCTOR_SQL = "UPDATE appointments SET status = ?, response =? WHERE sn = ?";
    private static final String COUNT_ALL_APPOINTMENTS_SQL = "SELECT COUNT(*) AS appointment_count FROM appointments";
    private static final String COUNT_ALL_APPOINTMENT_SQL = "SELECT COUNT(*) AS appointments_count FROM appointments WHERE status = ?";

    private SpecialityDao specialityDao = new SpecialityDao();
    private DoctorDao doctorDao = new DoctorDao();
    private UserDao userDao = new UserDao();
    public boolean insertAppointment(Appointment appointment) throws SQLException {
        System.out.println(INSERT_APPOINTMENT_SQL);
        boolean a = false;
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_APPOINTMENT_SQL)) {
            preparedStatement.setString(1, appointment.getUserID());
            preparedStatement.setString(2, appointment.getSpecialityID());
            preparedStatement.setDate(3, JDBCUtils.getSQLDate(appointment.getAppointmentDate()));
            preparedStatement.setString(4, appointment.getDescription());
            preparedStatement.setString(5, appointment.getStatus());

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            a = true;
        } catch (SQLException exception) {
            JDBCUtils.printSQLException(exception);
        }
        return a;
    }

    public List<Appointment> selectAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_APPOINTMENT_SQL)) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("sn");
                String userID = rs.getString("userid");
                String specialityID = rs.getString("speciality_id");
                String doctorID = rs.getString("doctor_id");
                LocalDate appointmentDate = rs.getDate("appointment_date").toLocalDate();
                String description = rs.getString("description");
                String status = rs.getString("status");
                String response = rs.getString("response");
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
                long appointmentID = rs.getLong("sn");
                String userID = rs.getString("userid");
                String specialityID = rs.getString("speciality_id");
                String doctorID = rs.getString("doctor_id");
                LocalDate appointmentDate = rs.getDate("appointment_date").toLocalDate();
                String description = rs.getString("description");
                String status = rs.getString("status");
                String response = rs.getString("response");

                userAppointments.add(new Appointment(appointmentID, userID, specialityID, doctorID, appointmentDate, description, status, response));
                System.out.println(userAppointments);
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
                long appointmentID = rs.getLong("sn");
                String userID = rs.getString("userid");
                userID = userDao.getUsersName(userID);
                String specialityID = rs.getString("speciality_id");
                specialityID = specialityDao.getSpecialityName(specialityID);
                String doctorID = rs.getString("doctor_id");
                doctorID = doctorDao.getDoctorName(doctorID);
                LocalDate appointmentDate = rs.getDate("appointment_date").toLocalDate();
                String description = rs.getString("description");
                String status = rs.getString("status");
                String response = rs.getString("response");

                doctorAppointments.add(new Appointment(appointmentID, userID, specialityID, doctorID, appointmentDate, description, status, response));

            }
        } catch (SQLException exception) {
            JDBCUtils.printSQLException(exception);
        }
        return doctorAppointments;
    }

    public List<Appointment> selectDoctorAppointment(String doctor) {
        List<Appointment> doctorAppointments = new ArrayList<>();
        String stat = "Pending";
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_DOCTOR_APPOINTMENTS_SQL)) {
            preparedStatement.setString(1, doctor);
            preparedStatement.setString(2, stat);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long appointmentID = rs.getLong("sn");
                String userID = rs.getString("userid");
                userID = userDao.getUsersName(userID);
                String specialityID = rs.getString("speciality_id");
                specialityID = specialityDao.getSpecialityName(specialityID);
                String doctorID = rs.getString("doctor_id");
                doctorID = doctorDao.getDoctorName(doctorID);
                LocalDate appointmentDate = rs.getDate("appointment_date").toLocalDate();
                String description = rs.getString("description");
                String status = rs.getString("status");
                String response = rs.getString("response");

                doctorAppointments.add(new Appointment(appointmentID, userID, specialityID, doctorID, appointmentDate, description, status, response));

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
                String userID = rs.getString("userid");
                String specialityID = rs.getString("speciality_id");
                String doctorID = rs.getString("doctor_id");
                LocalDate appointmentDate = rs.getDate("appointment_date").toLocalDate();
                String description = rs.getString("description");
                String status = rs.getString("status");
                String response = rs.getString("response");
            }
        } catch (SQLException exception) {
            JDBCUtils.printSQLException(exception);
        }
        return specialityAppointments;
    }

    public Appointment getAppointment(long aid) {
        Appointment appointment = null;

        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_APPOINTMENT_BY_ID_SQL)) {
            preparedStatement.setLong(1, aid);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                long id = rs.getLong("sn");
                String userID = rs.getString("userid");
                userID = userDao.getUsersName(userID);
                String specialityID = rs.getString("speciality_id");
                specialityID = specialityDao.getSpecialityName(specialityID);
                String doctorID = rs.getString("doctor_id");
                doctorID = doctorDao.getDoctorName(doctorID);
                LocalDate appointmentDate = rs.getDate("appointment_date").toLocalDate();
                String description = rs.getString("description");
                String status = rs.getString("status");
                String response = rs.getString("response");

                appointment = new Appointment(id, userID, specialityID, doctorID, appointmentDate, description, status, response);
            }
        } catch (SQLException exception) {
            JDBCUtils.printSQLException(exception);
        }
        return appointment;
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
            preparedStatement.setString(7, appointment.getResponse());
            preparedStatement.setLong(8, appointment.getAppointmentID());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public boolean doctorUpdateAppointment(Appointment appointment) throws SQLException {
        boolean doctorUpdated;
        try (Connection connection = JDBCUtils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_APPOINTMENT_BY_DOCTOR_SQL)){
            preparedStatement.setString(1, appointment.getStatus());
            preparedStatement.setString(2, appointment.getResponse());
            preparedStatement.setLong(3, appointment.getAppointmentID());
            System.out.println(preparedStatement);
            doctorUpdated = preparedStatement.executeUpdate() > 0;
        }
        return doctorUpdated;
    }

    public int totalAppointments() throws SQLException{
        int totalAppointments = 0;

        try (Connection connection = JDBCUtils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(COUNT_ALL_APPOINTMENTS_SQL)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                totalAppointments = resultSet.getInt("appointment_count");
            }
        }
        return totalAppointments;
    }

    public int totalAppointment() throws SQLException{
        int totalAppointment = 0;
        String status = "Awaiting";

        try (Connection connection = JDBCUtils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(COUNT_ALL_APPOINTMENT_SQL)){
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                totalAppointment = resultSet.getInt("appointments_count");
            }
        }
        return totalAppointment;
    }

}
