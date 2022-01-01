public class DriverController {
    Driver d;

    public Offer MakeOffer(int IndexOfRequestedRide,double Price)
    {
        Offer O = new Offer(d.getReqRides().get(IndexOfRequestedRide),Price,d);
        Driver.AddOffer(O);
        return O;
    }

    public boolean getDriver(String Username,String Password)
    {
        d =  Driver.getDriver(Username,Password);
        if(d!=null)
            return true;
        return false;
    }

    public void addDriver(String Username,String Password,String Email,String MobileNumber,String NationalID,String License)
    {
        Driver.AddDriver(Username,Password,Email,MobileNumber,NationalID,License);
    }

    public void AddPendingDriver(String Username,String Password,String Email, String MobileNumber,String NationalID,String License)
    {
        Driver.AddPendingDriver(Username,Password,Email,MobileNumber,NationalID,License);
    }

    public Driver getPendingDriver()
    {
        return Driver.getPendingDriver();
    }

    public void AddFavouriteArea(String Area)
    {
        d.AddFavouriteArea(Area);
    }

    public void modifyDriver()
    {
        Driver.modifyDriver(d);
    }

    public void EndRide()
    {
        d.setInRide(false);
        d.setBalance(d.getBalance()+d.getRideInProgress().getOffer().getPrice());
        d.getRideInProgress().EndRide();
        Driver.ModifyRide(d.getRideInProgress());
    }

    public Driver getD() {
        return d;
    }

    public void setD(Driver d) {
        this.d = d;
    }
}
