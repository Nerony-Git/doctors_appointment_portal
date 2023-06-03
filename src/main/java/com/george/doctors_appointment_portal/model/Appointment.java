package com.george.doctors_appointment_portal.model;

import java.time.LocalDate;

public class Appointment {
    private Long appointmentID;
    private String userID;
    private String specialityID;
    private String doctorID;
    private LocalDate appointmentDate;
    private String description;
    private String status;
    private String response;

    public Appointment(String userID, String specialityID, LocalDate appointmentDate, String description, String status) {
        super();
        this.userID = userID;
        this.specialityID = specialityID;
        this.appointmentDate = appointmentDate;
        this.description = description;
        this.status = status;

    }

    public Appointment(long id, String userID, String specialityID, String doctorID, LocalDate appointmentDate, String description, String status, String response) {
        super();
        this.appointmentID = id;
        this.userID = userID;
        this.specialityID = specialityID;
        this.doctorID = doctorID;
        this.appointmentDate = appointmentDate;
        this.description = description;
        this.status = status;
        this.response = response;

    }

    public Long getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(Long appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSpecialityID() {
        return specialityID;
    }

    public void setSpecialityID(String specialityID) {
        this.specialityID = specialityID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
