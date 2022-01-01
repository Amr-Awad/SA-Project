import java.util.ArrayList;

public abstract class User
{

    protected String Username;
    protected String MobileNumber;
    protected String Email;
    protected String Password;
    protected ArrayList<Ride> Rides ;
    protected Ride RideInProgress;
    protected boolean InRide;
    protected boolean NewNotifications;

    public void setRideInProgress(Ride rideInProgress) {
        RideInProgress = rideInProgress;
    }

    public User()
    {
        Username = "";
        MobileNumber = "";
        Email = "";
        Password = "";
        InRide = false;
        NewNotifications = false;
        Rides = new ArrayList<Ride>();
    }

    public User(String Username,String MobileNumber,String Password)
    {
        this.Username = Username;
        this.MobileNumber = MobileNumber;
        this.Password = Password;
        Email = "";
        InRide = false;
        NewNotifications = false;
        Rides = new ArrayList<Ride>();
    }

    public User(String Username,String MobileNumber,String Email,String Password)
    {
        this.Username = Username;
        this.MobileNumber = MobileNumber;
        this.Email = Email;
        this.Password = Password;
        InRide = false;
        NewNotifications = false;
        Rides = new ArrayList<Ride>();
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUsername() {
        return Username;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getEmail() {
        return Email;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPassword() {
        return Password;
    }

    public abstract void AddRide (Ride ride);
   /*{
        Rides.add(ride);
    }*/

    public Ride getRideInProgress() {
        return RideInProgress;
    }

    public void setInRide(boolean inRide) {
        InRide = inRide;
    }

    public boolean isInRide()
    {
        return InRide;
    }

    public boolean isNewNotifications()
    {
        return NewNotifications;
    }

    public void setNewNotifications(boolean newNotifications) {
        NewNotifications = newNotifications;
    }

    public ArrayList<Ride> getRides() {
        return Rides;
    }
}