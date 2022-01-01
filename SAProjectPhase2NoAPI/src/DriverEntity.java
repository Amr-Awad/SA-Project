import java.sql.*;

public class DriverEntity {
    private static int NumOfDriver = 1;
    private static int NumOfPendingDriver = 0;
    private static Connection conn = connect();

    public static Connection connect()
    {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:information.db";
            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {
            System.out.println("Wrong!!!");
        } catch (ClassNotFoundException e) {
            System.out.println("Wrong2!!!");
        }
        return conn;
    }

    public static RequestedRide getRequestedRide(int ID)
    {
        RequestedRide r = new RequestedRide();
        String sql = "Select * " +
                "from RequestedRide " +
                "where RequestedRideID = " + ID + ";";
        try {
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                r.setID(res.getInt("RequestedRideID"));
                r.setSource(res.getString("Soorce"));
                r.setDestination(res.getString("Destination"));
                int ClientID = res.getInt("RClientID");
                r.setClient(getClientInfo(ClientID));
            }
            return r;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static Offer getOffer(int ID, Driver d)
    {
        Offer r = new Offer();
        String sql = "Select * " +
                "from Offer " +
                "where OfferID = " + ID + ";";
        try {
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                r.setID(res.getInt("OfferID"));
                r.setPrice(res.getDouble("Price"));
                r.setRequestedRide(getRequestedRide(res.getInt("RequestedRideID")));
                r.setDriver(d);
            }
            return r;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static Driver getDriver(String Username, String Pass)
    {
        Driver d = null;
        Rating r = new Rating();
        String sql = "Select * " +
                "from Driver " +
                "where Driverusername = '" + Username + "' and DriverPassword = '" + Pass + "';";
        try {
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                d = new Driver();
                d.setUsername(res.getString("Driverusername"));
                d.setPassword(res.getString("DriverPassword"));
                d.setLicense(res.getString("DriverLicensenumber"));
                d.setEmail(res.getString("DriverEmail"));
                d.setNationalID(res.getString("DriverNationalID"));
                d.setMobileNumber(res.getString("DriverMobileNumber"));


                String sql2 = "Select * " +
                        "from Driver, [Rating ]" +
                        "where Driver.DriverRatingID = [Rating ].RatingID and Driver.DriverNationalID = '" + d.getNationalID() + "';";
                Statement stat2 = conn.createStatement();
                ResultSet res2 = stat2.executeQuery(sql2);
                while (res2.next()) {
                    r.setRateID(res2.getInt("RatingID"));
                    r.setRate(res2.getDouble("Rate"));
                    r.setRatingSum(res2.getDouble("RatingSum"));
                    r.setNumberOfRatings(res2.getInt("NumberofRatings"));
                }
                d.setRating(r);

                String sql3 = "Select * " +
                        "from Driver, RequestedRide, DriverRequestedRides " +
                        "where Driver.DriverNationalID = DriverRequestedRides.DriverID and RequestedRide.RequestedRideID = DriverRequestedRides.DriverRequestedRideID " + " and Driver.DriverNationalID = '" + d.getNationalID() + "';";
                Statement stat3 = conn.createStatement();
                ResultSet res3 = stat3.executeQuery(sql3);
                while (res3.next()) {
                    RequestedRide R = getRequestedRide(res3.getInt("RequestedRideID"));
                    d.UpdateRequestedRides(R);
                }

                String sql4 = "Select * " +
                        "from Driver, Areas, DriverFavouriteAreas " +
                        "where Driver.DriverNationalID = DriverFavouriteAreas.DriverID and Areas.Name = DriverFavouriteAreas.FavouriteArea " + " and Driver.DriverNationalID = '" + d.getNationalID() + "';";
                Statement stat4 = conn.createStatement();
                ResultSet res4 = stat4.executeQuery(sql4);

                while (res4.next()) {
                    d.AddFavouriteAreaList(res4.getString("FavouriteArea"));
                }

                String sql5 = "Select * " +
                        "from Driver, Ride, DriverRides " +
                        "where Driver.DriverNationalID = DriverRides.DriverID and Ride.RideID = DriverRides.RideID " + " and Driver.DriverNationalID = '" + d.getNationalID() + "';";
                Statement stat5 = conn.createStatement();
                ResultSet res5 = stat5.executeQuery(sql5);
                while (res5.next()) {
                    Ride R = new Ride();
                    R.setID(res5.getInt("RideID"));
                    R.setRated(res5.getBoolean("rated"));
                    R.setInProgress(res5.getBoolean("InProgress"));
                    R.setOffer(getOffer(res5.getInt("OfferID"), d));
                    d.AddRide(R);
                }

                String sql6 = "Select * " +
                        "from Driver, Ride " +
                        "where  Ride.RideID = Driver.RideInProgress " + " and Driver.DriverNationalID = '" + d.getNationalID() + "';";
                Statement stat6 = conn.createStatement();
                ResultSet res6 = stat6.executeQuery(sql6);
                Ride R = null;
                while (res6.next())
                {
                    R = new Ride();
                    R.setID(res6.getInt("RideID"));
                    R.setRated(res6.getBoolean("rated"));
                    R.setInProgress(res6.getBoolean("InProgress"));
                    R.setOffer(getOffer(res6.getInt("OfferID"), d));
                }
                d.setRideInProgress(R);

                d.setInRide(res.getBoolean("DriverInRide"));
                d.setNewNotifications(res.getBoolean("DriverNewNotification"));

                return d;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static Client getClientInfo(int ID)
    {
        Client c = new Client();
        String sql = "Select * " +
                "from Client " +
                "where ClientID = " + ID + ";";
        try {
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                c.setID(res.getInt("ClientID"));
                c.setUsername(res.getString("Clientusername"));
                c.setPassword(res.getString("ClientPassword"));
                c.setEmail(res.getString("ClientEmail"));
                c.setMobileNumber(res.getString("ClientMobileNumber"));
                c.setInRide(res.getBoolean("InRide"));
                c.setNewNotifications(res.getBoolean("NewNotifications"));

                String sql6 = "Select * " +
                        "from Client, Ride " +
                        "where  Ride.RideID = Client.RideInProgress " + " and Client.ClientID = '" + ID + "';";
                Statement stat6 = conn.createStatement();
                ResultSet res6 = stat6.executeQuery(sql6);
                Ride R = null;
                while (res6.next())
                {
                    String sql7 = "Select * " +
                            "from Offer" +
                            "where  OfferID = '" + res6.getInt("OfferID") + "';";
                    Statement stat7 = conn.createStatement();
                    ResultSet res7 = stat7.executeQuery(sql7);
                    res7.next();
                    R = new Ride();
                    R.setID(res6.getInt("RideID"));
                    R.setRated(res6.getBoolean("rated"));
                    R.setInProgress(res6.getBoolean("InProgress"));
                    R.setOffer(getOffer(res6.getInt("OfferID"), ClientEntity.getDriverinfo(res7.getString("DriverID"))));
                }
                c.setRideInProgress(R);

                return c;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void addDriver(String Username, String Pass, String Email, String MobileNumber, String NationalID, String License)
    {
        try {
            String sql, sql2;

            sql2 = "INSERT INTO [Rating ] ( RatingID ) VALUES ( NULL);";
            Statement stat2 = conn.createStatement();
            stat2.execute(sql2);


            if (Email == null)
                sql = "INSERT INTO Driver (" +
                        "DriverUsername, DriverMobileNumber, DriverPassword, DriverNationalID, DriverLicenseNumber, RatingID)" +
                        "VALUES ('" + Username + "','" + MobileNumber + "','" + Pass + "','" + NationalID + "','" + License + "','" + NumOfDriver + "');";
            else
                sql = "INSERT INTO Driver (" +
                        "DriverUsername ,DriverMobileNumber ,DriverEmail ,DriverPassword, DriverNationalID, DriverLicenseNumber, RatingID)" +
                        "VALUES ('" + Username + "','" + MobileNumber + "','" + Email + "','" + Pass + "','" + NationalID + "','" + License + "','" + NumOfDriver + "');";
            Statement stat = conn.createStatement();
            stat.execute(sql);

            NumOfDriver++;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void addPendingDriver(String Username, String Pass, String Email, String MobileNumber, String NationalID, String License)
    {
        try {
            String sql;

            if (Email == null)
                sql = "INSERT INTO PendingDrivers (" +
                        "DriverUsername, DriverMobileNumber, DriverPassword, DriverNationalID, DriverLicenseNumber)" +
                        "VALUES ('" + Username + "','" + MobileNumber + "','" + Pass + "','" + NationalID + "','" + License +  "');";
            else
                sql = "INSERT INTO PendingDrivers (" +
                        "DriverUsername ,DriverMobileNumber ,DriverEmail ,DriverPassword, DriverNationalID, DriverLicenseNumber)" +
                        "VALUES ('" + Username + "','" + MobileNumber + "','" + Email + "','" + Pass + "','" + NationalID + "','" + License + "');";
            Statement stat = conn.createStatement();
            stat.execute(sql);
            NumOfPendingDriver++;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Driver getPendingDriver()
    {
        {
            Driver d = new Driver();
            String sql = "Select LAST(*) " +
                    "from PendingDrivers ;" ;
            try {
                Statement stat = conn.createStatement();
                ResultSet res = stat.executeQuery(sql);
                while (res.next()) {
                    d.setUsername(res.getString("Driverusername"));
                    d.setPassword(res.getString("DriverPassword"));
                    d.setLicense(res.getString("DriverLicensenumber"));
                    d.setEmail(res.getString("DriverEmail"));
                    d.setNationalID(res.getString("DriverNationalID"));
                    d.setMobileNumber(res.getString("DriverMobileNumber"));
                    d.setInRide(res.getBoolean("DriverInRide"));
                    d.setNewNotifications(res.getBoolean("DriverNewNotification"));

                    String sql2 = "DELETE FROM PendingDrivers\n" +
                            "      WHERE \n" +
                            "            DriverNationalID ='"+ d.getNationalID()+"';\n" ;

                    Statement stat2 = conn.createStatement();
                    ResultSet res2 = stat2.executeQuery(sql);

                }


                return d;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return null;
        }
    }

    public static void addOffer(int RRID, String NationalID, double price)
    {
        String sql = "INSERT INTO Offer (DriverID,RequestedRideID, Price)" +
                "                  VALUES ('" + NationalID + "'," + RRID + "," + price + ");";
        try {
            Statement stat = conn.createStatement();
            stat.execute(sql);

            String sql3 = "Select * " +
                    "from Client ,RequestedRide " +
                    "where Client.ClientID = RequestedRide.RClientID and RequestedRide.RequestedRideID = " + RRID + ";";

            String sql4 = "Select max(OfferID)" +
                    "from Offer" + ";";

            Statement stat3 = conn.createStatement();
            ResultSet res3 = stat3.executeQuery(sql3);

            Statement stat4 = conn.createStatement();
            ResultSet res4 = stat4.executeQuery(sql4);
            res4.next();
            while (res3.next()) {

                String sql5 = "INSERT INTO ClientOffers (OfferID, ClientID)\n" +
                        "      VALUES ( " + res4.getInt("max(OfferID)") + "," + res3.getString("ClientID") + ");";

                Statement stat5 = conn.createStatement();
                stat5.execute(sql5);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void addFavarea(String NationalID, String Area)
    {
        String sql = "INSERT INTO DriverFavouriteAreas (FavouriteArea, DriverID)" +
                "                  VALUES ('"+ Area + "','" + NationalID + "');";
        try {
            Statement stat = conn.createStatement();
            stat.execute(sql);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void modifyDriver(Driver d)
    {

        String sql = "UPDATE Driver\n" +
                "   SET\n" +
                "       DriverInRide = '"+d.isInRide()+"',\n" +
                "       DriverNewNotification = '"+d.isNewNotifications()+"',\n" +
                "       DriverBalance = "+d.getBalance()+"\n" +
                " WHERE\n" +
                "       DriverNationalID ='"+d.getNationalID() +"'\n";
        try
        {
            Statement stat = conn.createStatement();
            stat.execute(sql);

            String sql2 = "UPDATE [Rating ]\n" +
                    "   SET\n" +
                    "       Rate = "+d.getRating().getRate()+",\n" +
                    "       RatingSum = "+d.getRating().getRatingSum()+",\n" +
                    "       NumberOfRatings = "+d.getRating().getNumberOfRatings()+"\n" +
                    " WHERE\n" +
                    "       RatingID ="+d.getRating().getRateID() +"\n";

            Statement stat2 = conn.createStatement();
            stat2.execute(sql2);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

    }

    public static void modifyRide(Ride r)
    {
        String sql = "UPDATE Ride\n" +
                "   SET\n" +
                "       InProgress = "+r.isInProgress()+",\n" +
                "       rated = "+r.isRated()+",\n" +
                " WHERE\n" +
                "       RideID ="+r.getID()+"\n";
        try
        {
            Statement stat = conn.createStatement();
            stat.execute(sql);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

}
