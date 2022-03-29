package edu.wpi.cs3733.D22.teamC;

import java.sql.*;

public class DBManager {
    public static DBManager instance;

    public Connection connection;

    public void startup(){
        instance = this;
        connectDatabase();
        setupLocationTable();
    }

    // Connect to embedded DB
    private void connectDatabase() {
        try {
            // Connect to DB
            connection = DriverManager.getConnection("jdbc:derby:DB;create=true;");
            Statement connectionStatement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("Apache Derby connection established!");
    }

    // Setup Location Table
    private void setupLocationTable() {
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet =
                    databaseMetaData.getTables(null, null, "LOCATION", new String[] {"TABLE"});

            if (resultSet.next()) {
                // Location Table exists, clear data
                Statement statement = connection.createStatement();
                statement.execute("DELETE FROM LOCATION");
            } else {
                // Location Table does not exist, create table
                Statement statement = connection.createStatement();
                statement.execute(
                        "CREATE TABLE LOCATION(nodeID char(16), xcoord int, ycoord int, floor char(4), building char(16), nodeType char(4), longName char(64), shortName char(32), Constraint nodeID_PK Primary Key (nodeID))");
            }
        } catch (SQLException e) {
            System.out.println("Table creation failed. Check output console.");
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
