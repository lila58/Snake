package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectDb {
    public static String url="jdbc:mysql://localhost:3306/snake";
    public static String user="root";
    public static String password="";

    public static Connection connect(){
        Connection conn=null;
        try {
            conn=DriverManager.getConnection(url,user,password);

        } catch (SQLException e) {
            System.out.println("Erreur de connexion");
            e.printStackTrace();
        }
        return conn;
    }

}
