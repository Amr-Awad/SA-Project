import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public  class DriverView {
    private static Scanner sc = new Scanner(System.in);
    private static boolean x = true;

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

    public static void DriverMenu(DriverController d) throws FileNotFoundException
    {
        int choice;
        if(d.getD().isInRide())
        {
            DriverInRide(d);
            return;
        }

        if(d.getD().isNewNotifications())
        {
            System.out.println("You have New Requested Rides Awaiting\n");
            d.getD().setNewNotifications(false);
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

            case 4:     System.out.println("Your Balance=  " + d.getD().getBalance());
                DriverMenu(d);
                break;

            case 5:     d.modifyDriver();
                Indriver.MainMenu();
                break;

            default:    System.out.println("Wrong Choice Entered \n");
                DriverMenu(d);
        }
    }

    public static void DriverInRide(DriverController d) throws FileNotFoundException
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
                    + "Client Username:  " + d.getD().getRideInProgress().getOffer().getRequestedRide().getClient().getUsername() + "\n"
                    + "Client Mobile Number:  " + d.getD().getRideInProgress().getOffer().getRequestedRide().getClient().getMobileNumber() + "\n");

                DriverInRide(d);
                return;


            case 2:     d.EndRide();
                DriverMenu(d);
                return;

            case 3:     Indriver.MainMenu();
                return;

            default:    System.out.println("Wrong Choice Entered");
                DriverMenu(d);
                return;
        }
    }

    public static void DriverCheckRequestedRides(DriverController d)
    {
        int choice;
        double price;
        for (int i = 0 ; i<d.getD().getRequestedRides().size() ; i++)
        {
            System.out.println("Requested Ride " + (i+1));
            System.out.println("Client Username:  " + d.getD().getRequestedRides().get(i).getClient().getUsername());
            System.out.println("Source Location:  "+d.getD().getRequestedRides().get(i).getSource());
            System.out.println("Destination Location:  "+d.getD().getRequestedRides().get(i).getDestination());

            while (true)
            {
                System.out.print("Do You Want To Make Offer on this Requested Ride? (1 for yes, 2 for no):  ");
                choice = sc.nextInt();

                if (choice == 1)
                {
                    ClientController ca = new ClientController();
                    System.out.print("Enter A Price for the Ride:  ");
                    price = sc.nextDouble();
                    d.MakeOffer(i,price);
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

    public static void DriverAddFavouriteAreas(DriverController d) throws FileNotFoundException
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

    public static void DriverRidesHistory(DriverController d)
    {
        for(int i=0 ; i< d.getD().getRides().size();i++)
        {

            System.out.println("Ride " + (i+1));
            System.out.println("Client Username:  " + d.getD().getRides().get(i).getOffer().getRequestedRide().getClient().getUsername());
            System.out.println("Client Mobile Number:  " + d.getD().getRides().get(i).getOffer().getRequestedRide().getClient().getMobileNumber());
            System.out.println("Source Location:  " + d.getD().getRides().get(i).getOffer().getRequestedRide().getSource());
            System.out.println("Destination Location:  " + d.getD().getRides().get(i).getOffer().getRequestedRide().getDestination());
            System.out.println("Ride Price:  " + d.getD().getRides().get(i).getOffer().getPrice() + "\n");

        }
        System.out.println("No More Rides!!!");
    }




}
