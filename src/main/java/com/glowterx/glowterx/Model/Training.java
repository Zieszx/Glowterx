package com.glowterx.glowterx.Model;

import java.sql.Date;

public class Training {
    private int id;
    private String name;
    private Date start_date;
    private Date end_date;
    private int duration;
    private int sessionNum;
    private int instructor_id;

    public Training() {
    }

    public Training(String name, Date start_date, Date end_date, int duration, int sessionNum, int instructor_id) {
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.duration = duration;
        this.sessionNum = sessionNum;
        this.instructor_id = instructor_id;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setSessionNum(int sessionNum) {
        this.sessionNum = sessionNum;
    }

    public int getSessionNum() {
        return sessionNum;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setInstructorID(int instructor_id) {
        this.instructor_id = instructor_id;
    }

    public int getInstructorId() {
        return instructor_id;
    }

}
