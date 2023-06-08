package com.george.doctors_appointment_portal.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Doctor implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userID;
    private String firstName;
    private String lastName;
    private String otherName;
    private String email;
    private String username;
    private String password;
    private String contact;
    private LocalDate dob;
    private String speciality;
    private String qualification;

    public Doctor(){

    }
    public Doctor(String userID, String firstName, String lastName, String otherName, LocalDate dob, String contact, String qualification, String email) {
        super();
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.otherName = otherName;
        this.dob = dob;
        this.contact = contact;
        this.qualification = qualification;
        this.email = email;
    }

    public Doctor(String userID, String firstName, String lastName, String otherName, String contact, String email) {
        super();
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.otherName = otherName;
        this.contact = contact;
        this.email = email;
    }

    public Doctor(String userID, String firstName, String lastName, String otherName, LocalDate dob, String contact, String specialty, String qualification, String email) {
        super();
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.otherName = otherName;
        this.dob = dob;
        this.contact = contact;
        this.speciality = specialty;
        this.qualification = qualification;
        this.email = email;
    }

    public Doctor(String userID, String firstName, String lastName, String otherName) {
        super();
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.otherName = otherName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
