import java.util.ArrayList;

public class Client extends User{
    private int ID;
    private ArrayList<Offer> Offers;
    //private static ClientEntity cdb = new ClientEntity();


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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ArrayList<Offer> getOffers() {
        return Offers;
    }

    public void UpdateOffer(Offer O)
    {
        Offers.add(O);
        NewNotifications = true;
    }

    public void addRequestedRide(RequestedRide ride)
    {
        ClientEntity.addRequestedRide (ride.getSource(),ride.getDestination(),ID);

    }

    public void AddRide (Ride ride)
    {
        Rides.add(ride);
        ride =  ClientEntity.addRide(ride);
    }

    public void UpdateRide()
    {
        ClientEntity.addFinishedRide(RideInProgress);
    }

    public static void modifyRideInProgress(Client c)
    {
        ClientEntity.modifyRideInProgress(c.RideInProgress,c);
    }


    public static Client AddClient(String Username,String Password,String Email, String MobileNumber)
    {
        ClientEntity.addClient(Username,Password, Email, MobileNumber);
        Client c = new Client(Username,MobileNumber,Email,Password);
        return c;
    }

    public static Client getClient(String Username,String Password)
    {
        Client c = ClientEntity.getClient(Username,Password);
        return c;
    }



    public static void modifyClient(Client c)
    {
        ClientEntity.modifyClient(c);
    }



}



