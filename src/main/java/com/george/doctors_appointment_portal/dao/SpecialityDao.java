package com.george.doctors_appointment_portal.dao;

import com.george.doctors_appointment_portal.model.Speciality;
import com.george.doctors_appointment_portal.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpecialityDao {

    private static final String SELECT_ALL_SPECIALITY = "SELECT * FROM speciality ORDER BY speciality_name ASC";
    private static final String SELECT_SPECIALITY_BY_ID = "SELECT * FROM speciality WHERE sid = ?";
    private static final String GET_SPECIALITY_NAME = "SELECT speciality_name FROM speciality WHERE sid = ?";
    private static final String INSERT_SPECIALITY = "INSERT INTO speciality (sid, speciality_name) VALUES (?, ?)";
    private static final String COUNT_ALL_SPECIALITY_SQL = "SELECT COUNT(*) AS speciality_count FROM speciality";
    private static final String UPDATE_SPECIALTY_SQL = "UPDATE speciality SET speciality_name = ? WHERE sid = ?";
    private static final String GET_LAST_SPECIALTY_ID = "SELECT sid FROM speciality ORDER BY sid DESC LIMIT 1";

    public int insertSpeciality(Speciality speciality) throws SQLException {
        int result = 0;
        try (Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SPECIALITY)){
            String lastSpecialtyID = getLastSpecialtyID();
            String newSpecialtyID;
            if (lastSpecialtyID == null) {
                newSpecialtyID = "SP0001"; // Default ID if no records exist
            } else {
                int id = Integer.parseInt(lastSpecialtyID.substring(2)) + 1;
                newSpecialtyID = "SP" + String.format("%04d", id);
            }

            preparedStatement.setString(1, newSpecialtyID);
            preparedStatement.setString(2, speciality.getSpecialityName());
            result = preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            JDBCUtils.printSQLException(exception);
        }
        return result;
    }

    public boolean updateSpecialty(Speciality speciality) throws SQLException {
        boolean s;

        try (Connection connection = JDBCUtils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SPECIALTY_SQL)){
            preparedStatement.setString(1, speciality.getSpecialityName());
            preparedStatement.setString(2, speciality.getsID());
            s = preparedStatement.executeUpdate() > 0;
        }
        return s;
    }

    public List<Speciality> getAllSpeciality() {

        List<Speciality> specialities = new ArrayList<Speciality>();

        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SPECIALITY)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String sID = resultSet.getString("sid");
                String specialityName = resultSet.getString("speciality_name");

                specialities.add(new Speciality(sID, specialityName));
            }
        } catch (SQLException exception) {
            JDBCUtils.printSQLException(exception);
        }
        return specialities;

    }

    public Speciality getSpecialityByID(String id) throws SQLException {
        Speciality speciality = null;
        try (Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SPECIALITY_BY_ID)){
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                speciality = new Speciality();
                speciality.setsID(resultSet.getString("sid"));
                speciality.setSpecialityName(resultSet.getString("speciality_name"));

            }
        }
        return speciality;
    }

    public String getSpecialityName(String id) throws SQLException {
        String specialityName = null;
        try (Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_SPECIALITY_NAME)){
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                specialityName = resultSet.getString("speciality_name");

                System.out.println(specialityName);
            }
        }
        return specialityName;
    }

    public int totalSpecialties() throws SQLException{
        int totalSpecialties = 0;

        try (Connection connection = JDBCUtils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(COUNT_ALL_SPECIALITY_SQL)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                totalSpecialties = resultSet.getInt("speciality_count");
            }
        }
        return totalSpecialties;
    }

    public String getLastSpecialtyID() throws SQLException {
        String lastSpecialtyID = null;

        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_LAST_SPECIALTY_ID)){
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                lastSpecialtyID = resultSet.getString("sid");
            }
        }
        return lastSpecialtyID;
    }
}
