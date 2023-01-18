package com.glowterx.glowterx.Model;

import java.sql.Date;

public class Training {
    private int id;
    private String name;
    private String start_date;
    private String end_date;
    private int training_session;
    private int training_duration;

    public Training() {
        this.id = 0;
        this.name = "";
        this.start_date = "";
        this.end_date = "";
        this.training_session = 0;
        this.training_duration = 0;
    }
    
    public Training(int id, String name, String start_date, String end_date, int training_session, int training_duration) {
        this.id = id;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.training_session = training_session;
        this.training_duration = training_duration;
    }
    
    public void setId (int id) {
        this.id = id;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setStart_date (String start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date (String end_date) {
        this.end_date = end_date;
    }

    public void setTraining_session (int training_session){
        this.training_session = training_session;
    }

    public void setTraining_duration (int training_duration) {
        this.training_duration = training_duration;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public int getTraining_session() {
        return training_session;
    }

    public int getTraining_duration() {
        return training_duration;
    }

}

