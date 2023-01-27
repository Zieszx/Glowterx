package com.glowterx.glowterx.Model;

import java.util.Date;

public class OrderUser {
    private int id;
    private int person_id;
    private int payment_id;
    private int order_quantity;
    private Date order_date;
    private String order_status;

    public OrderUser() {
    }

    public OrderUser(int id, int person_id, int payment_id, int order_quantity, Date order_date, String order_status) {
        this.id = id;
        this.person_id = person_id;
        this.payment_id = payment_id;
        this.order_quantity = order_quantity;
        this.order_date = order_date;
        this.order_status = order_status;
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

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public int getOrder_quantity() {
        return order_quantity;
    }

    public void setOrder_quantity(int order_quantity) {
        this.order_quantity = order_quantity;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

}
