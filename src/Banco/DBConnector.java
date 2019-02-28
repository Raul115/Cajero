/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco;

import java.sql.*;

/**
 *
 * @author Rulo
 */
public final class DBConnector {

    private String url;
    private final String db = "cajero";
    private final String user = "root";
    private final String psw = "root";
    private Statement st;

    public DBConnector() {
        DBConnection(db);
    }

    public Statement getSt() {
        return st;
    }

    public String getDb() {
        return db;
    }

    public void DBConnection(String db) {
        boolean existe = false;
        try {
            url = "jdbc:mysql://localhost:3306/" + db + "?autoReconnect=true&useSSL=false";
            Connection conn = DriverManager.getConnection(url, user, psw);
            if (conn != null) {
                existe = true;
                st = conn.createStatement();
            } else {
                existe = false;
            }
        } catch (SQLException es) {
        }
    }
}
