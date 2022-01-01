import java.io.FileNotFoundException;
import java.util.*;

public class Admin {

    private static Admin Instance = null;
    private static Scanner sc = new Scanner(System.in);
    private static int NumberOfPendingDrivers;
    private static String AdminName = "InDriver_Admin";
    private static String AdminPassword = "Admin123";

    private Admin()
    {
        NumberOfPendingDrivers = 0;
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
            case 1:     DriverChecking = getPendingDriver();
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
                    DriverController da = new DriverController();
                    da.addDriver(DriverChecking.getUsername(),DriverChecking.getPassword(),DriverChecking.getEmail(),DriverChecking.getMobileNumber(),DriverChecking.getNationalID(),DriverChecking.getLicense());
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

            case 2:     Indriver.MainMenu();
        }
    }

    public static Driver getPendingDriver()
    {
        if(NumberOfPendingDrivers>0)
        {
            Driver d ;
            DriverController Da = new DriverController();
            d = Da.getPendingDriver();
            NumberOfPendingDrivers--;
            return d;
        }
        return null;
    }

    public static Admin getInstance()
    {
        if (Instance == null)
        {
            Instance = new Admin();
        }
        return Instance;
    }

    public static boolean CheckAdminUsernameAndPassword(String AdminNam,String AdminPass)
    {
        if(AdminName.equalsIgnoreCase(AdminNam)&&AdminPassword.equals(AdminPass))
        {
            return true;
        }
        return false;
    }
}
