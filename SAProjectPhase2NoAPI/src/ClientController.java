public class ClientController {
    private Client c = null;

    public Client getC() {
        return c;
    }

    public void setC(Client c) {
        this.c = c;
    }

    public void AddClient(String Username,String Password,String Email,String MobileNumber)
    {
        c = Client.AddClient(Username, Password, Email, MobileNumber);
    }

    public boolean getClient(String Username,String Password)
    {
        c =  Client.getClient(Username,Password);
        if(c!=null)
            return true;
        return false;
    }

    public boolean RequestRide (String Source, String Destination)
    {
        RequestedRide r = new RequestedRide(c,Source,Destination);
        c.addRequestedRide(r);
        return true;
    }

    public void AcceptOffer(int indexOfferAccepted)
    {
        Ride R = new Ride(c.getOffers().get(indexOfferAccepted) );
        c.setInRide(true);
        c.setRideInProgress(R) ;
        R.StartRide();
        c.AddRide(c.RideInProgress);
        Client.modifyRideInProgress(c);
    }

    public void UpdateRide()
    {
        c.setInRide(false);
        c.UpdateRide();
    }

    public void ModifyClient()
    {
        Client.modifyClient(c);
    }

    public boolean Rate(double Rate,int indexRide)
    {
        if(!c.getRides().get(indexRide).isRated())
        {
            c.getRides().get(indexRide).setRated(true);
            c.getRides().get(indexRide).getOffer().getDriver().getRating().AddRate(Rate);
            return true;
        }
        return false;
    }








}
