package com.example.indriver.inDriver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverController {
    Driver d;
    @PostMapping("/AddOffer/{IndexOfRequestedRide}/{Price}/{Username}/{Password}")
    public Offer MakeOffer(@PathVariable int IndexOfRequestedRide,@PathVariable double Price, @PathVariable String Username, @PathVariable String Password)
    {
        d  = d.getDriver(Username , Password);
        Offer O = new Offer(d.getReqRides().get(IndexOfRequestedRide),Price,d);
        Driver.AddOffer(O);
        return O;
    }
    @GetMapping("getDriver/{Username}/{Password}")
    public boolean getDriver(@PathVariable String Username, @PathVariable String Password)
    {
        d =  Driver.getDriver(Username,Password);
        if(d!=null)
            return true;
        return false;
    }
    @PostMapping("/addDriver/{Username}/{Password}/{Email}/{MobileNumber}/{NationalID}/{License}")
    public void addDriver(@PathVariable String Username, @PathVariable String Password, @PathVariable String Email, @PathVariable String MobileNumber, @PathVariable String NationalID, @PathVariable String License)
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

    @PostMapping("/AddFavouriteArea/{Area}/{Username}/{Password}")
    public void AddFavouriteArea(@PathVariable String Area, @PathVariable String Username, @PathVariable String Password)
    {
        d  = d.getDriver(Username , Password);
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
