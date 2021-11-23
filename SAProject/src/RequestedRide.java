

public class RequestedRide
{

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

    public String getSource()
    {

        return Source;
    }

    public String getDestination()
    {

        return Destination;
    }
}
