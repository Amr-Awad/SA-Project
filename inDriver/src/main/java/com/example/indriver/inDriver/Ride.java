package com.example.indriver.inDriver;

public class Ride
{
    private int ID;



    private Offer O;
    private boolean InProgress;
    private boolean rated;

    public Ride()
    {
        rated = false;
    }

    public Ride(Offer O)
    {
        this.O = O;
        rated = false;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
    public Offer getOffer()
    {
        return O;
    }

    public void setOffer(Offer o) {
        O = o;
    }

    public void CalculateDistance()
    {}


    public void StartRide()
    {
        InProgress = true;
        NotifyDriverRideStarted();
    }

    public void EndRide()
    {
        InProgress = false;
        NotifyClientRideEnded();
    }

    private void NotifyDriverRideStarted()
    {

        O.getDriver().UpdateRide(this);
    }

    private void NotifyClientRideEnded()
    {
        ClientController ca = new ClientController();
        ca.setC(O.getRequestedRide().getClient());
        ca.UpdateRide();
    }

    public boolean isRated() {
        return rated;
    }

    public void setRated(boolean rated) {
        this.rated = rated;
    }

    public void setInProgress(boolean inProgress) {
        InProgress = inProgress;
    }

    public boolean isInProgress() {
        return InProgress;
    }
}
