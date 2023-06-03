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

    private static final String SELECT_ALL_SPECIALITY = "SELECT * FROM speciality";
    private static final String SELECT_SPECIALITY_BY_ID = "SELECT * FROM speciality WHERE sid = ?";
    private static final String GET_SPECIALITY_NAME = "SELECT speciality_name FROM speciality WHERE sid = ?";
    private static final String INSERT_SPECIALITY = "INSERT INTO speciality (sid, speciality_name) VALUES (?, ?)";

    public void insertSpeciality(Speciality speciality) throws SQLException {
        try (Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SPECIALITY)){
            preparedStatement.setString(1, speciality.getsID());
            preparedStatement.setString(2, speciality.getSpecialityName());
            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            JDBCUtils.printSQLException(exception);
        }
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

            while (resultSet.next()) {
                String sid = resultSet.getString("sid");
                String specialityName = resultSet.getString("speciality_name");

                speciality = new Speciality(sid, specialityName);
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
}
