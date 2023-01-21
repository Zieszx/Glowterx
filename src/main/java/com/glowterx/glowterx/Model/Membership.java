package com.glowterx.glowterx.Model;

import java.util.Date;

public class Membership {
    private int id;
    private String Category;
    private Date startdate;
    private Date enddate;
    private double amount;

    public Membership()
    {
    
    }

    public Membership ( int id, String Category, Date startdate , Date enddate, double amount)
    {
    this.id=id;
    this.Category=Category;
    this.startdate=startdate;
    this.enddate=enddate;
    this.amount=amount;
    }

    public int getID()
    {
        return id;
    }
    public void setID( int id)
    {
       this.id=id;
    }
    public String getCategory()
    {
        return Category;
    }
    public void setCategory( String Category)
    {
       this.Category=Category;
    }
    public Date startdate()
    {
        return startdate;
    }
    public void setstartdate(Date startdate)
    {
       this.startdate=startdate;
    }
    public Date enddate()
    {
        return enddate;
    }
    public void setenddate(Date enddate)
    {
       this.enddate=enddate;
    }
    public double getAmount()
    {
        return amount;
    }
    public void setAmount( double amount)
    {
       this.amount=amount;
    }
}