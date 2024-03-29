package com.glowterx.glowterx.Model;

import java.sql.Date;

public class Training {
    private int id;
    private String training_name;
    private Date start_date;
    private Date end_date;
    private int instructor_id;
    private int training_session;
    private int training_duration;

    public Training() {
    }

    public Training(String training_name, Date start_date, Date end_date, int training_duration, int training_session,
            int instructor_id) {
        this.training_name = training_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.training_duration = training_duration;
        this.training_session = training_session;
        this.instructor_id = instructor_id;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTraining_name(String training_name) {
        this.training_name = training_name;
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

    public String getTraining_name() {
        return training_name;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setTraining_session(int training_session) {
        this.training_session = training_session;
    }

    public int getTraining_session() {
        return training_session;
    }

    public void setTraining_duration(int training_duration) {
        this.training_duration = training_duration;
    }

    public int getTraining_duration() {
        return training_duration;
    }

    public void setInstructor_id(int instructor_id) {
        this.instructor_id = instructor_id;
    }

    public int getInstructor_id() {
        return instructor_id;
    }

}
