import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Indriver {

    private static ArrayList<Driver> drivers;
    private static ArrayList<Client> clients;

    private static Admin admin;

    private static Scanner sc;

    private static boolean x = true;
    private static RequestedRide r;


    public static boolean checkLocationExist(String Location) throws FileNotFoundException
    {
        String x = null;
        File myObj = new File("D://Locations.txt");
        Scanner myReader = new Scanner(myObj);

        while(myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] array = data.split(" ");
            String firstElement = array[0];
            int comp = firstElement.compareToIgnoreCase(Location);
            if (comp == 0) {
               return true;
            }
        }

        myReader.close();
        return false;
    }

    public static void NotifyDriver(RequestedRide r)
    {
        Indriver.r = r;
        for ( int i= 0; i < drivers.size()  ; i++ )
         {
             for(int j=0 ;j<drivers.get(i).getFavouriteAreas().size();j++)
             {
                 if(r.getSource().equalsIgnoreCase(drivers.get(i).getFavouriteAreas().get(j)))
                 {
                     drivers.get(i).UpdateRequestedRides(r);
                     break;
                 }
             }

         }

    }

    public static void NotifyClient(Offer O)
    {

        O.getRequestedRide().getClient().UpdateOffer(O);
    }

    public static void MainMenu() throws FileNotFoundException
    {

        int choice;
        String AName;
        String APass;
        System.out.println("1- Register\n" + "2- Login\n" + "3- Enter as an Admin");
        choice = sc.nextInt();

        switch (choice)
        {
            case 1:     Register();
                        break;

            case 2:     Login();
                        break;

            case 3:
                        System.out.println("Enter the Username of the admin");
                        AName = sc.next();
                        System.out.println("Enter the Password of the admin");
                        APass = sc.next();
                        if(Admin.CheckAdminUsernameAndPassword(AName,APass))
                        {
                            admin = Admin.getInstance();
                            System.out.println("Welcome Admin");
                            AdminMenu();
                        }
                        else
                        {
                            System.out.println("Wrong Username or Password Entered");
                            MainMenu();
                        }
                        break;

            default:    System.out.println("Wrong Choice Entered");
                        MainMenu();
                        break;
        }

    }

    public static void Register() throws FileNotFoundException
    {
        int choice;
        boolean AddEmail;
        String UserName;
        String Password;
        String Email;
        String MobileNumber;
        String License;
        String NationalID;
        Client c;
        Driver d;

        System.out.println("1- Register as a Client\n" + "2- Register as a Driver");
        choice = sc.nextInt();


        switch (choice)
        {
            case 1:     sc.nextLine();
                        System.out.print("Enter Username: ");
                        UserName = sc.nextLine();
                        System.out.print("Enter Password: ");
                        Password = sc.nextLine();
                        System.out.print("Enter Mobile Number: ");
                        MobileNumber = sc.next();
                        System.out.println("Do You Want To Add Email (enter true or false)");
                        AddEmail = sc.nextBoolean();
                        if(AddEmail)
                        {
                            System.out.print("Enter E-mail: ");
                            Email = sc.next();
                            c = new Client(UserName,MobileNumber,Email,Password);
                        }
                        else
                        {
                            c = new Client(UserName,MobileNumber,Password);
                        }
                        clients.add(c);
                        ClientMenu(clients.get(clients.size()-1));
                        break;

            case 2:     sc.nextLine();
                        System.out.print("Enter Username: ");
                        UserName = sc.nextLine();
                        System.out.print("Enter Password: ");
                        Password = sc.nextLine();
                        System.out.print("Enter Mobile Number: ");
                        MobileNumber = sc.next();
                        System.out.print("Enter License Number: ");
                        License = sc.next();
                        System.out.print("Enter NationalID Number: ");
                        NationalID = sc.next();
                        System.out.println("Do You Want To Add Email (enter true or false)");
                        AddEmail = sc.nextBoolean();
                        if(AddEmail)
                        {
                            System.out.print("Enter E-mail: ");
                            Email = sc.next();
                            d = new Driver(UserName,MobileNumber,Email,Password,License,NationalID);
                        }
                        else
                        {
                            d = new Driver(UserName,MobileNumber,Password,License,NationalID);
                        }
                        admin.AddPendingDrivers(d);
                        MainMenu();
                        break;

            default:    System.out.println("Wrong Choice Entered");
                        Register();
                        break;
        }

    }

    public static void Login() throws FileNotFoundException
    {
        int choice;
        String UserName;
        String Password;

        System.out.println("1- Login as a Client\n" + "2- Login as a Driver");
        choice = sc.nextInt();

        switch (choice)
        {
            case 1:     sc.nextLine();
                        System.out.println("Enter the Username of the Client");
                        UserName = sc.nextLine();
                        System.out.println("Enter the Password of the Client");
                        Password = sc.nextLine();
                        for(int i=0 ; i<clients.size() ; i++)
                        {
                            if(UserName.equalsIgnoreCase(clients.get(i).getUsername())&&Password.equals(clients.get(i).getPassword()))
                            {
                                System.out.println("Welcome "+clients.get(i).getUsername());
                                ClientMenu(clients.get(i));
                                return;
                            }
                        }
                            System.out.println("Wrong Username or Password Entered");
                            Login();
                            break;

            case 2:     sc.nextLine();
                        System.out.println("Enter the Username of the Driver");
                        UserName = sc.nextLine();
                        System.out.println("Enter the Password of the Driver");
                        Password = sc.nextLine();
                        for(int i=0 ; i<drivers.size() ; i++)
                        {
                            if(UserName.equalsIgnoreCase(drivers.get(i).getUsername())&&Password.equals(drivers.get(i).getPassword()))
                            {
                                System.out.println("Welcome "+drivers.get(i).getUsername());
                                DriverMenu(drivers.get(i));
                                return;
                            }
                        }
                        System.out.println("Wrong Username or Password Entered");
                        Login();
                        break;

            default:    System.out.println("Wrong Choice Entered");
                        Login();
                        break;
        }
    }

    public static void AdminMenu() throws FileNotFoundException
    {
        int choice , choice2;
        Driver DriverChecking ;
        System.out.println("Choose an Action from the Following Menu.\n" +
                           "1- Check a Pending Driver\n" +
                           "2- Log out");
        System.out.print("Choose Your Action:  ");
        choice = sc.nextInt();

        switch (choice)
        {
            case 1:     DriverChecking = admin.getPendingDriver();
                        if (DriverChecking == null)
                        {
                            System.out.println("There is no Pending Drivers");
                            AdminMenu();
                            break;
                        }
                        System.out.println("Driver Details:-\n"
                                          +"Driver Username:  " + DriverChecking.getUsername() + "\n"
                                          +"Driver Mobile Number:  " + DriverChecking.getMobileNumber() + "\n"
                                          +"Driver License:  " + DriverChecking.getLicense() + "\n"
                                          +"Driver National Id:  " + DriverChecking.getNationalID() + "\n");

                        System.out.println("Choose an Action from the Following Menu.\n" +
                                           "1- Accept Driver\n" +
                                           "2- Decline Driver");
                        System.out.print("Choose Your Action:  ");
                        choice2 = sc.nextInt();
                        if (choice2 == 1)
                        {
                            drivers.add(DriverChecking);
                            System.out.println("Driver Added \n");
                            AdminMenu();
                        }
                        else if (choice2 == 2)
                        {
                            System.out.println("Driver Removed \n");
                            AdminMenu();
                        }
                        else
                        {
                            System.out.println("Wrong Choice Entered \n");
                            AdminMenu();
                        }
                        break;

            case 2:     MainMenu();
        }
    }

    public static void ClientMenu(Client c) throws FileNotFoundException
    {
        int choice , choice2;
        if(c.isInRide())
        {
            ClientInRide(c);
            return;
        }

        if(c.isNewNotifications())
        {
            System.out.println("You have New Offers Awaiting\n");
            c.setNewNotifications(false);
        }

        System.out.println("Choose an Action from the Following Menu.\n" +
                           "1- Request Ride\n" +
                           "2- Check Offers\n" +
                           "3- Rides History\n"+
                           "4- Log out\n");
        System.out.print("Choose Your Action:  ");
        choice = sc.nextInt();

        switch (choice)
        {
            case 1:     ClientRequestRide(c);
                        ClientMenu(c);
                        break;

            case 2:     ClientCheckOffers(c);
                        ClientMenu(c);
                        break;

            case 3:     ClientRidesHistory(c);
                        ClientMenu(c);
                        break;

            case 4:     MainMenu();
                        break;

            default:    System.out.println("Wrong Choice Entered \n");
                        ClientMenu(c);
        }
    }

    public static void ClientInRide(Client c) throws FileNotFoundException
    {
    int choice;
        System.out.println("Your Ride is in progress Please wait till it finish");
        System.out.println("Choose an Action from the Following Menu.\n" +
                           "1- Check the Driver Details\n" +
                           "2- Log out");
        System.out.print("Choose Your Action:  ");
        choice = sc.nextInt();

        switch (choice) {
            case 1:
                System.out.println("Driver Details:-\n"
                        + "Driver Username:  " + c.getRideInProgress().getOffer().getDriver().getUsername() + "\n"
                        + "Driver Mobile Number:  " + c.getRideInProgress().getOffer().getDriver().getMobileNumber() + "\n"
                        + "Driver License:  " + c.getRideInProgress().getOffer().getDriver().getLicense() + "\n"
                        + "Driver National Id:  " + c.getRideInProgress().getOffer().getDriver().getNationalID() + "\n");
                ClientInRide(c);
                return;


            case 2:
                MainMenu();
                return;

            default:
                System.out.println("Wrong Choice Entered");
                ClientMenu(c);
                return;
        }
    }

    public static void ClientRequestRide(Client c) throws FileNotFoundException
    {
        String Source , Destination;
        sc.nextLine();
        while (true)
        {
            System.out.print("Please Enter your Source Location:  ");
            Source = sc.nextLine();
            if(!checkLocationExist(Source))
            {
                System.out.println("Location Not Found, Please Try Again");
                continue;
            }
            break;
        }
        while (true)
        {
            System.out.print("Please Enter your Destination Location:  ");
            Destination = sc.nextLine();
            if(!checkLocationExist(Destination))
            {
                System.out.println("Location Not Found, Please Try Again");
                continue;
            }
            break;
        }
        NotifyDriver(c.RequestRide(Source,Destination));
    }

    public static void ClientCheckOffers (Client c)
      {
        int choice;
        for (int i = 0 ; i<c.getOffers().size() ; i++)
        {
            System.out.println("Offer " + (i+1));
            System.out.println("Driver Username:  " + c.getOffers().get(i).getDriver().getUsername());
            System.out.println("Driver Rate:  " + c.getOffers().get(i).getDriver().getRating().getRate());
            System.out.println("Driver Number Of Rating:  " + c.getOffers().get(i).getDriver().getRating().getNumberOfRatings());
            System.out.println("Price Offered:  "+c.getOffers().get(i).getPrice());

            while (true)
            {
                System.out.print("Do You Want To Accept This Offer? (1 for yes, 2 for no):  ");
                choice = sc.nextInt();

                if (choice == 1)
                {
                    c.AcceptOffer(i);
                    return;
                }
                else if (choice == 2)
                {
                    break;
                }
                else
                {
                    System.out.println("Wrong Choice Entered");
                }

            }

        }
        System.out.println("No More Offers!!!");
        return;
    }

    public static void ClientRidesHistory(Client c)
    {
        int choice;
        for(int i=0 ; i< c.getRides().size();i++)
        {

            System.out.println("Ride " + (i+1));
            System.out.println("Driver Username:  " + c.getRides().get(i).getOffer().getDriver().getUsername());
            System.out.println("Driver Mobile Number:  " + c.getRides().get(i).getOffer().getDriver().getMobileNumber());
            System.out.println("Source Location:  " + c.getRides().get(i).getOffer().getRequestedRide().getSource());
            System.out.println("Destination Location:  " + c.getRides().get(i).getOffer().getRequestedRide().getDestination());
            System.out.println("Ride Price:  " + c.getRides().get(i).getOffer().getPrice());

             if(!(c.getRides().get(i).isRated()))
            {
                while (true)
                {
                    System.out.print("Do You Want To Rate This Ride ? (1 for yes, 2 for no):  ");
                    choice = sc.nextInt();

                    if (choice == 1)
                    {
                        double rate;
                        System.out.print("Enter Your Rate:  " );
                        rate = sc.nextDouble();
                        c.Rate(rate,i);
                        System.out.println("The Ride is Rated Successfully");
                        return;
                    }
                    else if (choice == 2)
                    {
                        break;
                    }
                    else
                    {
                        System.out.println("Wrong Choice Entered");
                    }
                }

            }
        }
        System.out.println("No More Rides!!!");
    }

    public static void DriverMenu(Driver d) throws FileNotFoundException
    {
        int choice;
        if(d.isInRide())
        {
            DriverInRide(d);
            return;
        }

        if(d.isNewNotifications())
        {
            System.out.println("You have New Requested Rides Awaiting\n");
            d.setNewNotifications(false);
        }

        System.out.println("Choose an Action from the Following Menu.\n" +
                "1- Check Requested Rides\n" +
                "2- Add Favourite Areas\n" +
                "3- Rides History\n"+
                "4- View Your Balance\n" +
                "5- Log out\n");
        System.out.print("Choose Your Action:  ");
        choice = sc.nextInt();

        switch (choice)
        {
            case 1:     DriverCheckRequestedRides(d);
                        DriverMenu(d);
                        break;

            case 2:     DriverAddFavouriteAreas(d);
                        DriverMenu(d);
                        break;

            case 3:     DriverRidesHistory(d);
                        DriverMenu(d);
                        break;

            case 4:     System.out.println("Your Balance=  " + d.getBalance());
                        DriverMenu(d);
                        break;

            case 5:     MainMenu();
                        break;

            default:    System.out.println("Wrong Choice Entered \n");
                        DriverMenu(d);
        }
    }

    public static void DriverInRide(Driver d) throws FileNotFoundException
    {
        int choice;
        System.out.println("Your Ride is in progress Please wait till it finish");
        System.out.println("Choose an Action from the Following Menu.\n" +
                "1- Check the Client Details\n" +
                "2- End Ride\n" +
                "3- Log out");
        System.out.print("Choose Your Action:  ");
        choice = sc.nextInt();

        switch (choice) {
            case 1:     System.out.println("Driver Details:-\n"
                                         + "Client Username:  " + d.getRideInProgress().getOffer().getRequestedRide().getClient().getUsername() + "\n"
                                         + "Client Mobile Number:  " + d.getRideInProgress().getOffer().getRequestedRide().getClient().getMobileNumber() + "\n");

                        DriverInRide(d);
                        return;


            case 2:     d.EndRide();
                        DriverMenu(d);
                        return;

            case 3:     MainMenu();
                        return;

            default:    System.out.println("Wrong Choice Entered");
                        DriverMenu(d);
                        return;
        }
    }

    public static void DriverCheckRequestedRides(Driver d)
    {
        int choice;
        double price;
        for (int i = 0 ; i<d.getRequestedRides().size() ; i++)
        {
            System.out.println("Requested Ride " + (i+1));
            System.out.println("Client Username:  " + d.getRequestedRides().get(i).getClient().getUsername());
            System.out.println("Source Location:  "+d.getRequestedRides().get(i).getSource());
            System.out.println("Destination Location:  "+d.getRequestedRides().get(i).getDestination());

            while (true)
            {
                System.out.print("Do You Want To Make Offer on this Requested Ride? (1 for yes, 2 for no):  ");
                choice = sc.nextInt();

                if (choice == 1)
                {
                    System.out.print("Enter A Price for the Ride:  ");
                    price = sc.nextDouble();
                    NotifyClient(d.MakeOffer(i,price));
                    return;
                }
                else if (choice == 2)
                {
                    break;
                }
                else
                {
                    System.out.println("Wrong Choice Entered");
                }

            }

        }
        System.out.println("No More Requested Rides!!!");
        return;
    }

    public static void DriverAddFavouriteAreas(Driver d) throws FileNotFoundException
    {
        int choice;
        String Location;
        System.out.print("Enter Your Location:  ");
        if(x) {
            sc.nextLine();
            x = false;
        }
        Location = sc.nextLine();
        if(!checkLocationExist(Location))
        {
            System.out.println("Location Not Found, Please Try Again");
            DriverAddFavouriteAreas(d);
        }
        else
        {
            d.AddFavouriteArea(Location);
            System.out.println("Favourite Area Added");
            while (true)
            {
                System.out.print("Do you want to Add more Favourite Areas? (1 for yes, 2 for no):  ");
                choice = sc.nextInt();
                x = true;

                if (choice == 1)
                {
                    DriverAddFavouriteAreas(d);
                    return;
                }
                else if (choice == 2)
                {

                    break;
                }
                else
                {
                    System.out.println("Wrong Choice Entered");
                }

            }
        }
    }

    public static void DriverRidesHistory(Driver d)
    {
        for(int i=0 ; i< d.getRides().size();i++)
        {

            System.out.println("Ride " + (i+1));
            System.out.println("Client Username:  " + d.getRides().get(i).getOffer().getRequestedRide().getClient().getUsername());
            System.out.println("Client Mobile Number:  " + d.getRides().get(i).getOffer().getRequestedRide().getClient().getMobileNumber());
            System.out.println("Source Location:  " + d.getRides().get(i).getOffer().getRequestedRide().getSource());
            System.out.println("Destination Location:  " + d.getRides().get(i).getOffer().getRequestedRide().getDestination());
            System.out.println("Ride Price:  " + d.getRides().get(i).getOffer().getPrice() + "\n");

        }
        System.out.println("No More Rides!!!");
    }

    public static void main(String [] args) throws FileNotFoundException {

        sc = new Scanner(System.in);
        admin = Admin.getInstance();
        drivers = new ArrayList<Driver>();
        clients = new ArrayList<Client>() ;


        System.out.println("Welcome To InDriver");
        MainMenu();
    }

}
