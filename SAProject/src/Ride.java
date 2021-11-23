public class Ride
{
    private Offer O;
    public static int Counter = 0;
    private int Id;
    private boolean InProgress;
    private boolean rated;

    public Ride()
    {
        Counter++;
        Id = Counter;
        rated = false;
    }

    public Ride(Offer O)
    {
        this.O = O;
        Counter++;
        Id = Counter;
        rated = false;
    }

    public Offer getOffer()
    {
        return O;
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
        O.getRequestedRide().getClient().UpdateRide();
    }

    public boolean isRated() {
        return rated;
    }

    public void setRated(boolean rated) {
        this.rated = rated;
    }
}
