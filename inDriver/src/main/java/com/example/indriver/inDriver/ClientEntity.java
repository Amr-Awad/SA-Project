package com.example.indriver.inDriver;

import java.sql.*;

public class ClientEntity {
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

    public static Offer getOffer(int ID)
    {
        Offer r = new Offer();
        Connection conn = connect();
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
                Driver d = getDriverinfo(res.getString("DriverID"));
                r.setDriver(d);
            }
            return r;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static Driver getDriverinfo(String ID)
    {
        Driver d = new Driver();
        Rating r = new Rating();
        Connection conn = connect();
        String sql = "Select * " +
                "from Driver " +
                "where DriverNationalID = '" + ID + "';";
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


                String sql2 = "Select * " +
                        "from Driver, Rating " +
                        "where Driver.RatingID = Rating.RatingID and " + " and Driver.DriverNationalID = '" + d.getNationalID() + "';";
                ResultSet res2 = stat.executeQuery(sql2);
                while (res2.next()) {
                    r.setRate(res2.getDouble("Rate"));
                    r.setRatingSum(res2.getDouble("RatingSum"));
                    r.setNumberOfRatings(res2.getInt("NumberofRatings"));
                }
                d.setRating(r);


                String sql4 = "Select * " +
                        "from Driver, Areas, DriverFavouriteAreas " +
                        "where Driver.NationalID = DriverFavouriteAreas.DriverID and Areas.Name = DriverFavouriteAreas.FavouriteArea " + " and Driver.DriverNationalID = '" + d.getNationalID() + "';";
                ResultSet res4 = stat.executeQuery(sql4);
                while (res4.next()) {
                    d.AddFavouriteArea(res4.getString("FavouriteArea"));
                }
            }
            return d;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static Client getClient(String Username, String Pass)
    {

        Client c = null;
        Connection conn = connect();
        String sql = "Select * " +
                "from Client " +
                "where ClientUsername = '" + Username + "' and ClientPassword = '" + Pass + "';";
        try {
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                c = new Client();
                c.setID(res.getInt("ClientID"));
                c.setUsername(res.getString("ClientUsername"));
                c.setPassword(res.getString("ClientPassword"));
                c.setEmail(res.getString("ClientEmail"));
                c.setMobileNumber(res.getString("ClientMobileNumber"));

                String sql3 = "Select * " +
                        "from Client, Ride, ClientRides " +
                        "where Client.ClientID = ClientRides.ClientID and Ride.RideID = ClientRides.RideID " + " and Client.ClientID = " + res.getInt("ClientID") + ";";
                Statement stat3 = conn.createStatement();
                ResultSet res3 = stat3.executeQuery(sql3);
                while (res3.next()) {
                    Ride R = new Ride();
                    R.setRated(res3.getBoolean("rated"));
                    R.setInProgress(res3.getBoolean("InProgress"));
                    R.setOffer(getOffer(res3.getInt("OfferID")));
                    c.AddRide(R);
                }


                String sql5 = "Select * " +
                        "from Client, Offer, ClientOffers " +
                        "where Client.ClientID = ClientOffers.ClientID and Offer.OfferID = ClientOffers.OfferID " + " and Client.ClientID = " + c.getID() + ";";
                Statement stat5 = conn.createStatement();
                ResultSet res5 = stat5.executeQuery(sql5);
                while (res5.next()) {
                    c.UpdateOffer(getOffer(res5.getInt("OfferID")));
                }

                String sql6 = "Select * " +
                        "from Client, Ride " +
                        "where  Ride.RideID = Client.RideInProgress " + " and Client.ClientID = " + c.getID() + ";";
                Statement stat6 = conn.createStatement();
                ResultSet res6 = stat6.executeQuery(sql6);
                Ride R = null;
                while (res6.next()) {
                    R = new Ride();
                    R.setID(res6.getInt("RideID"));
                    R.setRated(res6.getBoolean("rated"));
                    R.setInProgress(res6.getBoolean("InProgress"));
                    R.setOffer(getOffer(res6.getInt("OfferID")));
                }
                c.setRideInProgress(R);

                c.setInRide(res.getBoolean("InRide"));
                c.setNewNotifications(res.getBoolean("NewNotifications"));

            }
            return c;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void addClient(String Username, String Pass, String Email, String MobileNumber)
    {
        Connection conn = connect();
        try {
            String sql;
            if (Email == null)
                sql = "INSERT INTO Client (" +
                        "ClientUsername ,ClientMobileNumber  ,ClientPassword)" +
                        "VALUES ('" + Username + "','" + MobileNumber + "','" + Pass + "');";
            else
                sql = "INSERT INTO Client (" +
                        "ClientUsername ,ClientMobileNumber ,ClientEmail ,ClientPassword)" +
                        "VALUES ('" + Username + "','" + MobileNumber + "','" + Email + "','" + Pass + "');";
            Statement stat = conn.createStatement();
            stat.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static RequestedRide getRequestedRide(int ID)
    {
        RequestedRide r = new RequestedRide();
        Connection conn = connect();
        String sql = "Select * " +
                "from RequestedRide " +
                "where RequestedRideID = " + ID + ";";
        try {
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                r.setID(res.getInt("RequestedRideID"));
                r.setSource(res.getString("Source"));
                r.setDestination(res.getString("Destination"));
                int ClientID = res.getInt("ClientID");
                ResultSet res2 = stat.executeQuery("Select ClientUsername,ClientPassword " +
                        "from Client" +
                        "where ClientID = " + ClientID);
                res2.next();
                r.setClient(getClientInfo(res2.getInt("ClientID")));
            }
            return r;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void addRequestedRide(String Source, String Destination, int ClientId)
    {
        Connection conn = connect();
        try {
            String sql2 = "INSERT INTO RequestedRide (" +
                    "[Source ], Destination, ClientID)" +
                    "VALUES ('" + Source + "','" + Destination + "'," + ClientId + ");";

            Statement stat2 = conn.createStatement();
            stat2.execute(sql2);

            String sql3 = "Select * " +
                    "from Driver join DriverFavouriteAreas on DriverNationalID = DriverID " +
                    "join Areas on FavouriteArea = Name " +
                    "where Name = '" + Source +"';";/*\*/

            String sql4 = "Select max(RequestedRideID) " +
                    "from RequestedRide" + ";";

            Statement stat3 = conn.createStatement();
            ResultSet res3 = stat3.executeQuery(sql3);

            Statement stat4 = conn.createStatement();
            ResultSet res4 = stat4.executeQuery(sql4);
            while (res4.next())
            {
                while (res3.next()) {

                    String sql5 = "INSERT INTO DriverRequestedRides (RequestedRideID, DriverID)\n" +
                            "      VALUES ( " + res4.getInt("max(RequestedRideID)") + ",'" + res3.getString("DriverNationalID") + "');";

                    Statement stat5 = conn.createStatement();
                    stat5.execute(sql5);

                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static void addFinishedRide(Ride r)
    {
        Connection conn = connect();

        try {
            String sql3 = "Select * " +
                    "from Client ,RequestedRide" +
                    "where Client.ClientID=RequestedRide.ClientID and RequestedRide.RequestedRideID = " + r.getOffer().getRequestedRide().getID() + ";";

            String sql4 = "Select * " +
                    "from Driver ,Offer" +
                    "where Driver.DriverNationalID=Offer.DriverID and Offer.OfferID = " + r.getOffer().getID() + ";";


            Statement stat3 = conn.createStatement();
            ResultSet res3 = stat3.executeQuery(sql3);
            res3.next();

            Statement stat4 = conn.createStatement();
            ResultSet res4 = stat4.executeQuery(sql4);
            res4.next();


            String sql6 = "INSERT INTO ClientRides (RideID, ClientID)\n" +
                    "      VALUES ( " + r.getID() + "," + res3.getInt("ClientID") + ");";

            Statement stat6 = conn.createStatement();
            stat6.execute(sql6);

            String sql7 = "INSERT INTO DriverRides (RideID, DriverID)\n" +
                    "      VALUES ( " +r.getID() + ",'" + res4.getString("DriverID") + "');";

            Statement stat7 = conn.createStatement();
            stat7.execute(sql7);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }


    public static Ride addRide(Ride r)
    {
        Connection conn = connect();
        String sql = "INSERT INTO Ride (OfferID,InProgress, rated)" +
                "                  VALUES (" + r.getOffer().getID() + "," + r.isInProgress() + "," + r.isRated() + ");";
        try {
            Statement stat = conn.createStatement();
            stat.execute(sql);

            String sql2 = "Select max(RideID)" +
                    "from Ride" + ";";

            Statement stat2 = conn.createStatement();
            ResultSet res2 = stat2.executeQuery(sql2);
            res2.next();
            r.setID(res2.getInt("RideID"));

            return r;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }


    public static void modifyRideInProgress(Ride r)
    {
        Connection conn = connect();
        String sql = "UPDATE Client\n" +
                "   SET\n" +
                "       ClientInRide = "+r.getOffer().getRequestedRide().getClient().isInRide()+",\n" +
                "       RideInProgress = "+r.getOffer().getRequestedRide().getClient().RideInProgress.getID()+",\n" +
                " WHERE\n" +
                "       ClientID ="+r.getOffer().getRequestedRide().getClient().getID() +"\n";

        String sql2 = "UPDATE Driver\n" +
                "   SET\n" +
                "       DriverInRide = "+r.getOffer().getDriver().isInRide()+",\n" +
                "       RideInProgress = "+r.getOffer().getDriver().RideInProgress.getID()+",\n" +
                " WHERE\n" +
                "       DriverNationalID ='"+r.getOffer().getDriver().getNationalID() +"'\n";
        try {
            Statement stat = conn.createStatement();
            stat.execute(sql);

            Statement stat2 = conn.createStatement();
            stat2.execute(sql2);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static void modifyClient(Client c)
    {

        Connection conn = connect();
        String sql;
        if(c.RideInProgress==null)
        {
            sql = "UPDATE Client\n" +
                    "   SET\n" +
                    "       InRide = '"+c.isInRide()+"',\n" +
                    "       NewNotifications = '"+c.isNewNotifications()+"' "+
                    " WHERE\n" +
                    "       ClientID ="+c.getID() +";\n";
        }
        else
        {
             sql = "UPDATE Client\n" +
                    "   SET\n" +
                    "       InRide = '"+c.isInRide()+"',\n" +
                    "       NewNotifications = '"+c.isNewNotifications()+"',\n" +
                    "       RideInProgress = "+c.RideInProgress.getID()+
                    " WHERE ClientID ="+c.getID() +";\n";
        }
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

    public static Client getClientInfo(int ID)
    {
        Client c = new Client();
        Connection conn = connect();
        String sql = "Select * " +
                "from Client " +
                "where ClientID = " + ID + ";";
        try {
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                c.setUsername(res.getString("Clientusername"));
                c.setPassword(res.getString("ClientPassword"));
                c.setEmail(res.getString("ClientEmail"));
                c.setMobileNumber(res.getString("ClientMobileNumber"));
                c.setInRide(res.getBoolean("DriverInRide"));
                c.setNewNotifications(res.getBoolean("DriverNewNotification"));
            }
            return c;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
