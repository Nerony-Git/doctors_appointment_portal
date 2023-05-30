package com.george.doctors_appointment_portal.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {
    private static String jdbcURL = "jdbc:postgresql://localhost:5432/dpaApp";
    private static String jdbcUsername = "postgres";
    private static String jdbcPassword = "Neron@123";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.ds.PGConnectionPoolDataSource");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    public static  void  printSQLException(SQLException ex) {
        for (Throwable e:ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}