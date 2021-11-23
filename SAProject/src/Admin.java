import java.util.*;

public class Admin {

    private static Admin Instance = null;

    private static ArrayList<Driver> PendingDrivers;
    private int NumberOfPendingDrivers;
    private static String AdminName = "InDriver_Admin";
    private static String AdminPassword = "Admin123";

    private Admin()
    {
        PendingDrivers = new ArrayList<Driver>();
        NumberOfPendingDrivers = 0;
    }

    public Driver getPendingDriver()
    {
        if(NumberOfPendingDrivers>0)
        {
            Driver D = PendingDrivers.get(NumberOfPendingDrivers - 1);
            PendingDrivers.remove(NumberOfPendingDrivers - 1);
            NumberOfPendingDrivers--;
            return D;
        }
        return null;
    }

    public void AddPendingDrivers(Driver D)
    {
        PendingDrivers.add(D);
        NumberOfPendingDrivers++;
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
