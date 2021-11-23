import java.util.ArrayList;

public class Client extends User{
    private ArrayList<Offer> Offers;


    public Client()
    {
        super();
        Offers = new ArrayList<Offer>();
    }

    public Client(String Username,String MobileNumber,String Password)
    {
        super(Username,MobileNumber,Password);
        Offers = new ArrayList<Offer>();

    }

    public Client(String Username,String MobileNumber,String Email,String Password)
    {
        super(Username,MobileNumber,Email,Password);
        Offers = new ArrayList<Offer>();

    }

    public RequestedRide RequestRide (String Source,String Destination)
    {
        RequestedRide r = new RequestedRide(this,Source,Destination);
        return r;
    }

    public void AcceptOffer(int indexOfferAccepted)
    {
        Ride R = new Ride(Offers.get(indexOfferAccepted));
        InRide = true;
        RideInProgress  = R;
        R.StartRide();
    }

    public void UpdateOffer(Offer O)
    {
        Offers.add(O);
        NewNotifications = true;
    }

    public void UpdateRide()
    {
        InRide = false;
        AddRide(RideInProgress);
    }

    public ArrayList<Offer> getOffers() {
        return Offers;
    }

    public void Rate(double Rate,int indexRide)
    {
            Rides.get(indexRide).setRated(true);
            Rides.get(indexRide).getOffer().getDriver().getRating().AddRate(Rate);
    }

}
