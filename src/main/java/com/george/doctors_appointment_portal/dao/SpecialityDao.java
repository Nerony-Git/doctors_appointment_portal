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
}
