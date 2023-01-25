package com.glowterx.glowterx.Model;

public class Attendance {
    int id;
    int person_id;
    int training_id;
    String attendance_status;
    int attendance_checkins;

    public Attendance() {
    }

    public Attendance(int person_id, int training_id, String attendance_status, int attendance_checkins) {
        this.person_id = person_id;
        this.training_id = training_id;
        this.attendance_status = attendance_status;
        this.attendance_checkins = attendance_checkins;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public int getTraining_id() {
        return training_id;
    }

    public void setTraining_id(int training_id) {
        this.training_id = training_id;
    }

    public String getAttendance_status() {
        return attendance_status;
    }

    public void setAttendance_status(String attendance_status) {
        this.attendance_status = attendance_status;
    }

    public int getAttendance_checkins() {
        return attendance_checkins;
    }

    public void setAttendance_checkins(int attendance_checkins) {
        this.attendance_checkins = attendance_checkins;
    }

    
}
