package com.example.indriver.inDriver;

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


    /*public static void NotifyDriver(RequestedRide r)
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

    }*/

    /*public static void NotifyClient(Offer O)
    {

        O.getRequestedRide().getClient().UpdateOffer(O);
    }*/

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
                            Admin.AdminMenu();
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
        String Email = null;
        String MobileNumber;
        String License;
        String NationalID;
        ClientController ca = new ClientController();
        DriverController da = new DriverController();
        Driver d ;

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
                        }
                        ca.AddClient(UserName, Password, Email, MobileNumber);
                        ClientView.ClientMenu(ca);
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

                        }
                        da.AddPendingDriver(UserName,Password,Email,MobileNumber,NationalID,License);
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
        ClientController ca = new ClientController();
        DriverController da = new DriverController();

        System.out.println("1- Login as a Client\n" + "2- Login as a Driver");
        choice = sc.nextInt();

        switch (choice)
        {
            case 1:     sc.nextLine();
                        System.out.println("Enter the Username of the Client");
                        UserName = sc.nextLine();
                        System.out.println("Enter the Password of the Client");
                        Password = sc.nextLine();

                        if(ca.getClient(UserName,Password))
                        {
                            System.out.println("Welcome " + ca.getC().getUsername());
                            ClientView.ClientMenu(ca);
                            break;
                        }
                        System.out.println("Wrong Username or Password Entered");
                        Login();
                        break;

            case 2:     sc.nextLine();
                        System.out.println("Enter the Username of the Driver");
                        UserName = sc.nextLine();
                        System.out.println("Enter the Password of the Driver");
                        Password = sc.nextLine();
                        if(da.getDriver(UserName,Password))
                        {
                            System.out.println("Welcome " + da.getD().getUsername());
                            DriverView.DriverMenu(da);
                            break;
                        }
                        System.out.println("Wrong Username or Password Entered");
                        Login();
                        break;

            default:    System.out.println("Wrong Choice Entered");
                        Login();
                        break;
        }
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