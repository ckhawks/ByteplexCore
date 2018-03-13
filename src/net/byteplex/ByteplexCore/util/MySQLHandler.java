package net.byteplex.ByteplexCore.util;

import java.sql.*;
import java.util.UUID;

public class MySQLHandler {
    private static Connection connection;

    public static void test() {


        ResultSet result;
        try {
            connect();
            result = doQuery("SELECT * FROM gangs;");
            if (result == null) {
                // do nothing pl0x
            } else {
                while (result.next()) {
                    System.out.println("Gang ID: " + result.getInt("gangid"));
                    System.out.println("Gang name: " + result.getString("gangname"));
                    System.out.println("Gang tag: " + result.getString("gangtag"));
                    UUID leaderUUID = UUID.fromString(result.getString("gangleader"));
                    System.out.println("Leader UUID: " + leaderUUID.toString());
                }
            }
            disconnect();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/corpquest", "dev", "development6");
    }

    public static void disconnect() throws SQLException {
        connection.close();
    }

    public static ResultSet doQuery(String query) throws SQLException {
        //Class.forName("com.mysql.jdbc.Driver");
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
