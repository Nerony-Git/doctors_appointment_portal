package com.george.doctors_appointment_portal.dao;

import com.george.doctors_appointment_portal.model.Doctor;
import com.george.doctors_appointment_portal.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorDao {

    private static final String INSERT_DOCTOR_SQL = "INSERT INTO doctors (userid, first_name, last_name, other_name, email, username, password, contact, dob, speciality, qualification) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
    private static final String GET_DOCTOR_NAME = "SELECT first_name, last_name, other_name FROM doctors WHERE userid = ?";
    public int registerDoctor(Doctor doctor) throws ClassNotFoundException {

        int result = 0;
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DOCTOR_SQL)) {
            preparedStatement.setString(1, doctor.getUserID());
            preparedStatement.setString(2, doctor.getFirstName());
            preparedStatement.setString(3, doctor.getLastName());
            preparedStatement.setString(4, doctor.getOtherName());
            preparedStatement.setString(5, doctor.getEmail());
            preparedStatement.setString(6, doctor.getUsername());
            preparedStatement.setString(7, doctor.getPassword());
            preparedStatement.setString(8, doctor.getContact());
            preparedStatement.setString(9, doctor.getDob());
            preparedStatement.setString(10, doctor.getSpeciality());
            preparedStatement.setString(11, doctor.getQualification());

            System.out.println(preparedStatement);
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        return result;
    }

    public Doctor validateDoctor(String username, String password) throws SQLException, ClassNotFoundException{
        Doctor doctor = null;
        String LOGIN_DOCTOR = "SELECT * FROM doctors WHERE username = ? and password = ?";

        Class.forName("org.postgresql.ds.PGConnectionPoolDataSource");
        try (Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_DOCTOR)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                System.out.println(preparedStatement);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    doctor = new Doctor();
                    doctor.setUsername(rs.getString("username"));
                    doctor.setFirstName(rs.getString("first_name"));
                }
            } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        return doctor;
    }

    public String getDoctorName(String id) throws SQLException {
        String doctorName = null;
        if (id != null && !id.isEmpty()){

            try (Connection connection = JDBCUtils.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(GET_DOCTOR_NAME)){
                preparedStatement.setString(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()){
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String otherName = resultSet.getString("other_name");

                    doctorName = firstName + " " + otherName + " " + lastName;
                    System.out.println(doctorName);
                }
            }
        }else {
            doctorName = "Not yet Assigned";
        }
        return doctorName;
    }
}
