package com.george.doctors_appointment_portal.dao;

import com.george.doctors_appointment_portal.model.Doctor;
import com.george.doctors_appointment_portal.model.User;
import com.george.doctors_appointment_portal.utils.JDBCUtils;

import javax.print.Doc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorDao {

    private static final String LOGIN_DOCTOR = "SELECT * FROM doctors WHERE username = ? and password = ?";
    private static final String INSERT_DOCTOR_SQL = "INSERT INTO doctors (userid, first_name, last_name, other_name, email, username, password, contact, dob, speciality, qualification) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
    private static final String GET_DOCTOR_NAME = "SELECT first_name, last_name, other_name FROM doctors WHERE userid = ?";
    private static final String GET_LAST_USER_ID = "SELECT userid FROM doctors ORDER BY userid DESC LIMIT 1";
    private static final String OLD_PASSWORD_SQL = "SELECT * FROM doctors WHERE userid = ? and password = ?";
    private static final String UPDATE_PASSWORD_SQL = "UPDATE doctors SET password = ? WHERE userid = ?";
    private static final String UPDATE_DOCTOR_SQL = "UPDATE doctors SET first_name = ?, last_name = ?, other_name = ?, dob = ?, contact = ?, qualification = ?, email = ? WHERE userid = ?";
    private static final String DOCTOR_BY_ID = "SELECT * FROM doctors WHERE userid = ?";
    private static final String SELECT_ALL_DOCTORS_SQL = "SELECT * FROM doctors ORDER BY sn ASC";
    private static final String COUNT_ALL_DOCTORS_SQL = "SELECT COUNT(*) AS doctor_count FROM doctors";

    private SpecialityDao specialityDao = new SpecialityDao();

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
                    String speciality = rs.getString("speciality");
                    speciality = specialityDao.getSpecialityName(speciality);
                    doctor.setSpeciality(speciality);
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

    public boolean validateDoctorOldPassword(String userID, String oldPassword) throws SQLException {
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

    public boolean changeDoctorPassword(String userID, String newPassword) throws SQLException {
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

    public boolean updateDoctor(Doctor doctor) throws SQLException {
        boolean u;

        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DOCTOR_SQL)){
            preparedStatement.setString(1, doctor.getFirstName());
            preparedStatement.setString(2, doctor.getLastName());
            preparedStatement.setString(3, doctor.getOtherName());
            preparedStatement.setDate(4, JDBCUtils.getSQLDate(doctor.getDob()));
            preparedStatement.setString(5, doctor.getContact());
            preparedStatement.setString(6, doctor.getQualification());
            preparedStatement.setString(7, doctor.getEmail());
            preparedStatement.setString(8, doctor.getUserID());
            System.out.println(preparedStatement);
            u = preparedStatement.executeUpdate() > 0;
        }
        return u;
    }

    public Doctor getDoctorByID(String userID) throws SQLException {
        Doctor doctor = null;

        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DOCTOR_BY_ID)){
            preparedStatement.setString(1, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                doctor = new Doctor();

                doctor.setUserID(resultSet.getString("userid"));
                doctor.setFirstName(resultSet.getString("first_name"));
                doctor.setLastName(resultSet.getString("last_name"));
                doctor.setOtherName(resultSet.getString("other_name"));
                doctor.setUsername(resultSet.getString("username"));
                doctor.setDob(resultSet.getDate("dob").toLocalDate());
                doctor.setContact(resultSet.getString("contact"));
                doctor.setQualification(resultSet.getString("qualification"));
                doctor.setEmail(resultSet.getString("email"));

            }
        }
        return doctor;
    }

    public int totalDoctors() throws SQLException{
        int totalDoctors = 0;

        try (Connection connection = JDBCUtils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(COUNT_ALL_DOCTORS_SQL)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                totalDoctors = resultSet.getInt("doctor_count");
            }
        }
        return totalDoctors;
    }

    public List<Doctor> getAllDoctors() throws SQLException {
        List<Doctor> allDoctors = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_DOCTORS_SQL)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String userID = resultSet.getString("userid");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String otherName = resultSet.getString("other_name");
                String contact = resultSet.getString("contact");
                String email = resultSet.getString("email");

                allDoctors.add(new Doctor(userID, firstName, lastName, otherName, contact, email));

            }
        }
        return allDoctors;
    }

}
