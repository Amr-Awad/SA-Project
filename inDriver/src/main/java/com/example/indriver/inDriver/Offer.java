package com.example.indriver.inDriver;

public class Offer
{
    private int ID;
    private RequestedRide R;
    private double Price;
    private Driver D;

    public Offer()
    {
        Price = 0.0;
    }

    public Offer(RequestedRide R, double Price, Driver D)
    {
        this.R = R;
        this.D = D;
        this.Price = Price;
    }


    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setPrice(double price)
    {
        Price = price;
    }

    public void setDriver(Driver d)
    {
        D = d;
    }

    public void setRequestedRide(RequestedRide r)
    {
        R = r;
    }

    public Driver getDriver()
    {

        return D;
    }

    public RequestedRide getRequestedRide()
    {

        return R;
    }

    public double getPrice() {
        return Price;
    }
}


