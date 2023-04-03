package com.projet.badr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    static String user = "root";
    static String password = "";
    static String url = "jdbc:mysql://127.0.0.1:3306/badrdb";
    static String driver = "com.mysql.cj.jdbc.Driver";
    public static Connection getConnection(){
        Connection con = null;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url,user,password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return con;
    }
}

