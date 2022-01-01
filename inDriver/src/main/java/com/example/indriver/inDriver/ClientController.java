package com.example.indriver.inDriver;

import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {
    private Client c = null;
    ClientController(){
        Client c  = new Client();
    }

    public Client getC() {
        return c;
    }

    public void setC(Client c) {
        this.c = c;
    }
    @PostMapping("/addClient/{Username}/{Password}/{Email}/{MobileNumber}")
    public Boolean AddClient(@PathVariable String Username,@PathVariable String Password,@PathVariable String Email,@PathVariable String MobileNumber)
    {
        c = Client.AddClient(Username, Password, Email, MobileNumber);
        if (c != null )
        {
            return true;
        }
        else return false;
    }
   /* http://localhost:8080/client/add/joe/12385/ibtesarf/216
            "Username" : "dfhfd",
            "MobileNumber" : "012" ,
            "Email" : "ibrahim",
            "Password" : "123"*/

    public Boolean getClient( String Username, String Password)
    {
        c = Client.getClient(Username,Password);
        if(c!=null)
            return true;
        return false;
    }
    @PostMapping("/requestRide/{Source}/{Destination}/{UserName}/{Password}")
    public boolean RequestRide (@PathVariable String Source,@PathVariable String Destination, @PathVariable String UserName , @PathVariable String Password)
    {
        c = c.getClient(UserName , Password);
        RequestedRide r = new RequestedRide(c,Source,Destination);
        c.addRequestedRide(r);
        return true;
    }


    /*"Username" : "joe",
    "MobileNumber" : "216" ,
    "Email" : "ibtesarf",
    "Password" : "12385"*/
    @PostMapping("/acceptOffer/{indexOfferAccepted}/{UserName}/{Password}")
    public void AcceptOffer(@PathVariable int indexOfferAccepted , @PathVariable String UserName , @PathVariable String Password)
    {
        c = c.getClient(UserName , Password);
        Ride R = new Ride(c.getOffers().get(indexOfferAccepted));
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
