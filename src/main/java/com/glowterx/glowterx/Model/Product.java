package com.glowterx.glowterx.Model;

public class Product {
    private int id;
    private String prod_name;
    private double prod_price;
    private int prod_quantity;
    private String prod_category;
    private String prod_status;

    public Product() {
    }

    public Product(String prod_name, double prod_price, int prod_quantity, String prod_category, String prod_status) {
        this.prod_name = prod_name;
        this.prod_price = prod_price;
        this.prod_quantity = prod_quantity;
        this.prod_category = prod_category;
        this.prod_status = prod_status;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public double getProd_price() {
        return prod_price;
    }

    public void setProd_price(double prod_price) {
        this.prod_price = prod_price;
    }

    public int getProd_quantity() {
        return prod_quantity;
    }

    public void setProd_quantity(int prod_quantity) {
        this.prod_quantity = prod_quantity;
    }

    public String getProd_category() {
        return prod_category;
    }

    public void setProd_category(String prod_category) {
        this.prod_category = prod_category;
    }

    public String getProd_status() {
        return prod_status;
    }

    public void setProd_status(String prod_status) {
        this.prod_status = prod_status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
