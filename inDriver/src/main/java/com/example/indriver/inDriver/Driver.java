package com.example.indriver.inDriver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
@RestController
public class Driver extends User{
     private String License;
     private String NationalID;
     private ArrayList<String> FavouriteAreas;
     private ArrayList<RequestedRide> ReqRides;
     public Rating R;
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
    @Override
    public void AddRide(Ride ride) {

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

    public static void ModifyRide(Ride r)
    {
        DriverEntity.modifyRide(r);
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

    public void setRating(Rating r) {
        R = r;
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

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ArrayList<RequestedRide> getReqRides() {
        return ReqRides;
    }


    public static void AddOffer(Offer o)
    {
        DriverEntity.addOffer(o.getRequestedRide().getID(),o.getDriver().getNationalID(),o.getPrice());
    }

    public ArrayList<String> getFavouriteAreas()
    {
        return FavouriteAreas ;
    }

    public ArrayList<RequestedRide> getRequestedRides() {
        return ReqRides;
    }

    @GetMapping("getDriver/{Username}/{Password}")
    public static Driver getDriver(@PathVariable String Username,@PathVariable String Password)
    {
        Driver d = DriverEntity.getDriver(Username,Password);
        return d;
    }

    public static Driver AddDriver( String Username, String Password, String Email,  String MobileNumber,  String NationalID, String License)
    {
        DriverEntity.addDriver(Username,Password, Email, MobileNumber,NationalID,License);
        Driver d = new Driver(Username,MobileNumber,Password,License,NationalID);
        return d;
    }

    public static void AddPendingDriver(String Username,String Password,String Email, String MobileNumber,String NationalID,String License)
    {
        DriverEntity.addPendingDriver(Username,Password, Email, MobileNumber,NationalID,License);
    }

    public static Driver getPendingDriver()
    {
        return DriverEntity.getPendingDriver();
    }


    public void AddFavouriteAreaList(String Area)
    {
        FavouriteAreas.add(Area);
    }

    public void AddFavouriteArea(String Area)
    {
        DriverEntity.addFavarea(NationalID,Area);
        FavouriteAreas.add(Area);
    }

    public double getBalance() {
        return balance;
    }

    public static void modifyDriver(Driver d)
    {
        DriverEntity.modifyDriver(d);
    }
}
