package com.glowterx.glowterx.Model;

public class Cart {
    private int id;
    private int quantity;
    private int person_id;
    private int product_id;

    public Cart(int quantity, int person_id, int product_id) {
        this.quantity = quantity;
        this.person_id = person_id;
        this.product_id = product_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    
}
