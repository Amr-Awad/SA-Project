package com.example.indriver.inDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ClientView {
    private static Scanner sc = new Scanner(System.in);

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
    public static void ClientMenu(ClientController c) throws FileNotFoundException
    {
        int choice , choice2;
        if(c.getC().isInRide())
        {
            ClientInRide(c);
            return;
        }

        if(c.getC().isNewNotifications())
        {
            System.out.println("You have New Offers Awaiting\n");
            c.getC().setNewNotifications(false);
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

            case 4:
                c.ModifyClient();
                Indriver.MainMenu();
                break;

            default:    System.out.println("Wrong Choice Entered \n");
                ClientMenu(c);
        }
    }

    public static void ClientInRide(ClientController c) throws FileNotFoundException
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
                        + "Driver Username:  " + c.getC().getRideInProgress().getOffer().getDriver().getUsername() + "\n"
                        + "Driver Mobile Number:  " + c.getC().getRideInProgress().getOffer().getDriver().getMobileNumber() + "\n"
                        + "Driver License:  " + c.getC().getRideInProgress().getOffer().getDriver().getLicense() + "\n"
                        + "Driver National Id:  " + c.getC().getRideInProgress().getOffer().getDriver().getNationalID() + "\n");
                ClientInRide(c);
                return;


            case 2:
                Indriver.MainMenu();
                return;

            default:
                System.out.println("Wrong Choice Entered");
                ClientMenu(c);
                return;
        }
    }

    public static void ClientRequestRide(ClientController c) throws FileNotFoundException
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
       //c.RequestRide(Source,Destination);
    }

    public static void ClientCheckOffers (ClientController c)
    {
        int choice;
        for (int i = 0 ; i<c.getC().getOffers().size() ; i++)
        {
            System.out.println("Offer " + (i+1));
            System.out.println("Driver Username:  " + c.getC().getOffers().get(i).getDriver().getUsername());
            System.out.println("Driver Rate:  " + c.getC().getOffers().get(i).getDriver().getRating().getRate());
            System.out.println("Driver Number Of Rating:  " + c.getC().getOffers().get(i).getDriver().getRating().getNumberOfRatings());
            System.out.println("Price Offered:  "+c.getC().getOffers().get(i).getPrice());

            while (true)
            {
                System.out.print("Do You Want To Accept This Offer? (1 for yes, 2 for no):  ");
                choice = sc.nextInt();

                if (choice == 1)
                {
                    //c.AcceptOffer(i);
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

    public static void ClientRidesHistory(ClientController c)
    {
        int choice;
        for(int i=0 ; i< c.getC().getRides().size();i++)
        {

            System.out.println("Ride " + (i+1));
            System.out.println("Driver Username:  " + c.getC().getRides().get(i).getOffer().getDriver().getUsername());
            System.out.println("Driver Mobile Number:  " + c.getC().getRides().get(i).getOffer().getDriver().getMobileNumber());
            System.out.println("Source Location:  " + c.getC().getRides().get(i).getOffer().getRequestedRide().getSource());
            System.out.println("Destination Location:  " + c.getC().getRides().get(i).getOffer().getRequestedRide().getDestination());
            System.out.println("Ride Price:  " + c.getC().getRides().get(i).getOffer().getPrice());

            if(!(c.getC().getRides().get(i).isRated()))
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
                        if(c.Rate(rate,i))
                            System.out.println("The Ride is Rated Successfully");
                        else
                            System.out.println("This Ride has Already been Rated before");
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




}
