import java.util.ArrayList;

public class Driver extends User{
    private String License;
    private String NationalID;
    private ArrayList<String> FavouriteAreas;
    private ArrayList<RequestedRide> ReqRides;
    private Rating R;
    private double balance;

    public Driver()
    {
        super();
        FavouriteAreas = new ArrayList<String>();
        ReqRides = new ArrayList<RequestedRide>();
        License = "";
        NationalID = "";
        R = new Rating();
        balance = 0;
    }

    public Driver(String Username,String MobileNumber,String Password,String License,String NationalID)
    {

        super(Username,MobileNumber,Password);
        FavouriteAreas = new ArrayList<String>();
        FavouriteAreas.add("Hi");
        ReqRides = new ArrayList<RequestedRide>();
        this.License = License;
        this.NationalID = NationalID;
        R = new Rating();
        balance = 0;
    }

    public Driver(String Username,String MobileNumber,String Email,String Password,String License,String NationalID)
    {
        super(Username,MobileNumber,Email,Password);
        FavouriteAreas = new ArrayList<String>();
        ReqRides = new ArrayList<RequestedRide>();
        this.License = License;
        this.NationalID = NationalID;
        R = new Rating();
        balance = 0;
    }

    public void setLicense(String license)
    {

        License = license;
    }

    public String getLicense()
    {

        return License;
    }

    public void setNationalID(String nationalID)
    {

        NationalID = nationalID;
    }

    public String getNationalID()
    {

        return NationalID;
    }

    public Rating getRating()
    {

        return R;
    }

     public void UpdateRide(Ride R)
     {
         RideInProgress = R;
         InRide = true;
     }

    public void UpdateRequestedRides(RequestedRide R)
    {
        ReqRides.add(R);
        NewNotifications = true;
    }

     public Offer MakeOffer(int IndexOfRequestedRide,double Price)
     {
         Offer O = new Offer(ReqRides.get(IndexOfRequestedRide),Price,this);
         return O;
     }



     public void EndRide()
     {
         InRide = false;
         balance+=RideInProgress.getOffer().getPrice();
         RideInProgress.EndRide();
         AddRide(RideInProgress);

     }

    public ArrayList<String> getFavouriteAreas()
    {
        return FavouriteAreas ;
    }

    public ArrayList<RequestedRide> getRequestedRides() {
        return ReqRides;
    }

    public void AddFavouriteArea(String Area)
    {
        FavouriteAreas.add(Area);
    }

    public double getBalance() {
        return balance;
    }
}
