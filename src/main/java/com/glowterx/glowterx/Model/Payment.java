package com.glowterx.glowterx.Model;

import java.util.Date;

public class Payment {
    private int id;
    private int person_id;
    private double amount;
    private Date payment_date;
    private String payment_status;
    private String payment_category;

    public Payment() {
    }

    public Payment(int id, int person_id, double amount, Date payment_date, String payment_status,
            String payment_category) {
        this.id = id;
        this.person_id = person_id;
        this.amount = amount;
        this.payment_date = payment_date;
        this.payment_status = payment_status;
        this.payment_category = payment_category;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(Date payment_date) {
        this.payment_date = payment_date;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getPayment_category() {
        return payment_category;
    }

    public void setPayment_category(String payment_category) {
        this.payment_category = payment_category;
    }

}
