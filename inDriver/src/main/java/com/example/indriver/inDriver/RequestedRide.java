package com.example.indriver.inDriver;

public class RequestedRide
{
    public int getID() {
        return ID;
    }

    private int ID;

    public void setID(int ID) {
        this.ID = ID;
    }

    private Client c;
    private String Source;
    private String Destination;

    RequestedRide()
    {
        Source = "";
        Destination = "";
    }

    RequestedRide(Client c,String Source,String Destination)
    {
        this.c=c;
        this.Source = Source;
        this.Destination = Destination;
    }

    public Client getClient()
    {

        return c;
    }

    public void setSource(String source) {
        Source = source;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public void setClient(Client c) {
        this.c = c;
    }

    public String getSource()
    {

        return Source;
    }

    public String getDestination()
    {

        return Destination;
    }
}
