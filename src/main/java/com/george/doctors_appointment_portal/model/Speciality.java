package com.george.doctors_appointment_portal.model;

public class Speciality {
    private String sID;
    private String specialityName;

    public Speciality(String sID, String specialityName) {
        super();
        this.sID = sID;
        this.specialityName = specialityName;
    }

    public Speciality() {

    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }
}
