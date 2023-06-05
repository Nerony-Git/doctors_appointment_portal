package com.george.doctors_appointment_portal.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userID;
    private String firstName;
    private String lastName;
    private String otherName;
    private LocalDate dob;
    private String email;
    private String contact;
    private String username;
    private String password;
    private int totalUsers;
    private int totalDoctors;
    private int totalAppointments;
    private int totalAppointment;
    private int totalSpeciality;

    public Admin(){

    }
    public Admin(String userID, String firstName, String lastName, String otherName, LocalDate dob, String contact, String email) {
        super();
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.otherName = otherName;
        this.dob = dob;
        this.contact = contact;
        this.email = email;
    }

    public int getTotalSpeciality() {
        return totalSpeciality;
    }

    public void setTotalSpeciality(int totalSpeciality) {
        this.totalSpeciality = totalSpeciality;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public int getTotalDoctors() {
        return totalDoctors;
    }

    public void setTotalDoctors(int totalDoctors) {
        this.totalDoctors = totalDoctors;
    }

    public int getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(int totalAppointments) {
        this.totalAppointments = totalAppointments;
    }

    public int getTotalAppointment() {
        return totalAppointment;
    }

    public void setTotalAppointment(int totalAppointment) {
        this.totalAppointment = totalAppointment;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
