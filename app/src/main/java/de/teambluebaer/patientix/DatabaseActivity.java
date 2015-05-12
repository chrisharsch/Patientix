package de.teambluebaer.patientix;

import android.app.Activity;
import android.os.Bundle;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseActivity extends Activity {

    private Connection con;

    private String host = "localhost";
    private String port = "3306";
    private String db = "UMMMobilPatient";
    private String user = "root";
    private String pw = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
    }

    public DatabaseActivity() {

        // Auf Treiber überprüfen
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

        } catch (Exception ex) {
            new AssertionError("Es wurde kein Datenbanktreiber gefunden!");
        }

        // DB Verbindung herstellen
        try {
            String url = "jdbc:mysql://" + host + ":" + port + "/" + db + "?user=" + user + "&password=" + pw;
            con = DriverManager.getConnection(url);
        } catch (SQLException e) {
            new AssertionError("Es kann keine Verbindung zur Datenbank hergestellt werden!");
        }
    }

    public boolean update(String query) {
        // Update, Insert, Delete
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            new AssertionError("Statement konnte nicht erstellt werden!");
            return false;
        }
    }

    public ResultSet select(String query) {
        // Select
        try {
            Statement stmt = con.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            new AssertionError("Statement konnte nicht erstellt werden!");
            return null;
        }
    }

    public void close() {
        try {
            con.close();
        } catch (SQLException e) {
            new AssertionError("Verbindung konnte nicht geschlossen werden!");
        }
    }

    //Getter and setter for the LoginActivity
    public Connection getCon() {
        return con;
    }

}
