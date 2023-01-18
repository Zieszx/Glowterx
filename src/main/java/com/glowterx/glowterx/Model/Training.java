package com.glowterx.glowterx.Model;

import java.sql.Date;

public class Training {
    private int id;
    private String name;
    private String start_date;
    private String end_date;

    public Training() {
        this.id = 0;
        this.name = "";
        this.start_date = "";
        this.end_date = "";
    }
    
    public Training(int id, String name, String start_date, String end_date) {
        this.id = id;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
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

}

