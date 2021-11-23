public class Offer
{
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


