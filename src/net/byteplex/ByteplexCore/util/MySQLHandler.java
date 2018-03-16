package net.byteplex.ByteplexCore.util;

import net.byteplex.ByteplexCore.ByteplexCore;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;


public class MySQLHandler {
    private static Connection connection;
    private static ByteplexCore plugin = ByteplexCore.getPlugin(ByteplexCore.class);
    public static String host, database, username, password;
    public static int port;

    private static FileConfiguration mysqlConfig;
    private static File mysqlConfigFile;

    public static Connection getConnection() {
        return connection;
    }

    public static void loadConfig() {

        // check if config file exists - if not, create it and populate with defaults
        // if it does exist, load values.
        mysqlConfigFile = new File(plugin.getDataFolder(), "mysql.yml");
        if (!mysqlConfigFile.exists()) {
            mysqlConfigFile.getParentFile().mkdirs();
            plugin.saveResource("mysql.yml", false);
        }

        mysqlConfig = new YamlConfiguration();
        try {
            mysqlConfig.load(mysqlConfigFile);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void connect() throws SQLException {
        // setup config file
        loadConfig();

        // load from config.yml
        host = mysqlConfig.getString("host");
        database = mysqlConfig.getString("database");
        username = mysqlConfig.getString("username");
        password = mysqlConfig.getString("password");
        port = mysqlConfig.getInt("port");

        try {
            synchronized (plugin) {
                if (getConnection() != null && !getConnection().isClosed()) {
                    return;
                }

                Class.forName("com.mysql.jdbc.Driver");
                // connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/corpquest", "dev", "development6");
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
                plugin.getServer().broadcastMessage("MySQL Connected!");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void disconnect() throws SQLException {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static ResultSet doGetQuery(String query) throws SQLException {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void doPostQuery(String query) throws SQLException {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e){

        }
    }

    public static void printAllGangs() {
        ResultSet result;
        try {
            result = doGetQuery("SELECT * FROM gangs;");
            if (result == null) {
                // do nothing pl0x
            } else {
                while (result.next()) {
                    System.out.println("Gang ID: " + result.getInt("gangid"));
                    System.out.println("Gang name: " + result.getString("gangname"));
                    System.out.println("Gang tag: " + result.getString("gangtag"));

                    UUID leaderUUID = UUID.fromString(result.getString("gangleader").replaceFirst (
                            "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                            "$1-$2-$3-$4-$5")
                    );

                    System.out.println("Leader UUID: " + leaderUUID.toString());

                    System.out.println("Parsed uuid: " + Bukkit.getOfflinePlayer(leaderUUID).getName());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
