package de.hdm.itprojekt.noteit.server.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.google.appengine.api.utils.SystemProperty;

/**
 * @author Thies
 */
public class DBConnection {

    private static Connection con = null;

    private static String googleUrl = "jdbc:google:mysql://noteit-id:noteit-db/noteit?user=root";
    private static String localUrl = "jdbc:mysql://104.199.44.232:3306/noteit-db?user=root&password=noteit";
    
    public static Connection connection() {
        if (con == null) {
            String url = null;
            try {
                if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
                    // Load the class that provides the new
                    // "jdbc:google:mysql://" prefix.
                    Class.forName("com.mysql.jdbc.GoogleDriver");
                    url = googleUrl;
                } else {
                    // Local MySQL instance to use during development.
                    Class.forName("com.mysql.jdbc.Driver");
                    url = localUrl;
                }
                System.out.println("getConnection: " + url);
                con = DriverManager.getConnection(url);
                
            } catch (Exception e) {
                con = null;
                e.printStackTrace();
            }
        }

        return con;
    }

}