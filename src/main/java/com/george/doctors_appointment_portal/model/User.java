package com.george.doctors_appointment_portal.model;

import java.io.Serializable;
import java.time.LocalDate;

public class User {
    private String userID;
    private String firstName;
    private String lastName;
    private String otherName;
    private String username;
    private LocalDate dob;
    private String contact;
    private String address;
    private String postalAddress;
    private String email;
    private String password;

    public User() {

    }

    public User(String userID, String firstName, String lastName, String otherName, LocalDate dob, String contact, String address, String postalAddress, String email) {
        super();
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.otherName = otherName;
        this.dob = dob;
        this.contact = contact;
        this.address = address;
        this.postalAddress = postalAddress;
        this.email = email;
    }

    public User(String userID, String firstName, String lastName, String otherName, String username, LocalDate dob, String contact, String address, String postalAddress, String email, String password) {
        super();
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.otherName = otherName;
        this.username = username;
        this.dob = dob;
        this.contact = contact;
        this.address = address;
        this.postalAddress = postalAddress;
        this.email = email;
        this.password = password;
    }

    public User(String userID, String firstName, String lastName, String otherName, String username, LocalDate dob, String contact, String address, String postalAddress, String email) {
        super();
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.otherName = otherName;
        this.username = username;
        this.dob = dob;
        this.contact = contact;
        this.address = address;
        this.postalAddress = postalAddress;
        this.email = email;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }
}
