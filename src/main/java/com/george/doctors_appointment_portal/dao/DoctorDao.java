package com.george.doctors_appointment_portal.dao;

import com.george.doctors_appointment_portal.model.Doctor;
import com.george.doctors_appointment_portal.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorDao {

    private static final String LOGIN_DOCTOR = "SELECT * FROM doctors WHERE username = ? and password = ?";
    private static final String INSERT_DOCTOR_SQL = "INSERT INTO doctors (userid, first_name, last_name, other_name, email, username, password, contact, dob, speciality, qualification) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
    private static final String GET_DOCTOR_NAME = "SELECT first_name, last_name, other_name FROM doctors WHERE userid = ?";
    private static final String GET_LAST_USER_ID = "SELECT userid FROM doctors ORDER BY userid DESC LIMIT 1";
    public int registerDoctor(Doctor doctor) throws ClassNotFoundException {

        int result = 0;
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DOCTOR_SQL)) {
            String lastUserID = getLastUserID();
            String newUserID;
            if (lastUserID == null) {
                newUserID = "DOC0001"; // Default ID if no records exist
            } else {
                int id = Integer.parseInt(lastUserID.substring(3)) + 1;
                newUserID = "DOC" + String.format("%04d", id);
            }

            preparedStatement.setString(1, newUserID);
            preparedStatement.setString(2, doctor.getFirstName());
            preparedStatement.setString(3, doctor.getLastName());
            preparedStatement.setString(4, doctor.getOtherName());
            preparedStatement.setString(5, doctor.getEmail());
            preparedStatement.setString(6, doctor.getUsername());
            preparedStatement.setString(7, doctor.getPassword());
            preparedStatement.setString(8, doctor.getContact());
            preparedStatement.setDate(9, JDBCUtils.getSQLDate(doctor.getDob()));
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

        Class.forName("org.postgresql.ds.PGConnectionPoolDataSource");
        try (Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_DOCTOR)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                System.out.println(preparedStatement);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    doctor = new Doctor();
                    doctor.setUserID(rs.getString("userid"));
                    doctor.setFirstName(rs.getString("first_name"));
                    doctor.setLastName(rs.getString("last_name"));
                    doctor.setOtherName(rs.getString("other_name"));
                    doctor.setUsername(rs.getString("username"));
                    doctor.setDob(rs.getDate("dob").toLocalDate());
                    doctor.setContact(rs.getString("contact"));
                    doctor.setSpeciality(rs.getString("speciality"));
                    doctor.setQualification(rs.getString("qualification"));
                    doctor.setEmail(rs.getString("email"));
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

}
