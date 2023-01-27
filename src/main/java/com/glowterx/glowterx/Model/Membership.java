package com.glowterx.glowterx.Model;

import java.util.Date;

public class Membership {
    private int id;
    private String membership_category;
    private Date start_date;
    private Date end_date;
    private double amount;
    private int person_id;
    private int payment_id;

    public Membership() {

    }

    public Membership(int id, String membership_category, Date start_date, Date end_date, double amount) {
        this.id = id;
        this.membership_category = membership_category;
        this.start_date = start_date;
        this.end_date = end_date;
        this.amount = amount;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getmembership_category() {
        return membership_category;
    }

    public void setmembership_category(String membership_category) {
        this.membership_category = membership_category;
    }

    public Date getstart_date() {
        return start_date;
    }

    public void setstart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getend_date() {
        return end_date;
    }

    public void setend_date(Date end_date) {
        this.end_date = end_date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}