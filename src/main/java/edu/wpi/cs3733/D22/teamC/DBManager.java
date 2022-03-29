package edu.wpi.cs3733.D22.teamC;

import java.sql.*;

/**
 * DBManager serves as manager class for interacting with the Database.
 */
public class DBManager {
    // Singleton Instance
    public static DBManager instance;

    // Database Connection
    public Connection connection;

    /**
     * Startup DB Manager according to the Singleton Model
     */
    public static void startup(){
        if (instance == null) {
            instance = new DBManager();
            instance.connectDatabase();
            instance.initializeLocationTable();
        }
    }

    /**
     * Connect to database.
     */
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

    /**
     * Checks if a Table of the given name exists. If it does, clear its contents.
     * If it doesn't, execute the CREATE statement.
     * @param tableName Name of table to initialize.
     * @param sqlCreateStatement SQL CREATE Statement to run in case of non-existence.
     */
    private void initializeTable(String tableName, String sqlCreateStatement) {
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(null, null, tableName, new String[] {"TABLE"});

            if (resultSet.next()) {
                // Table exists, clear data from table
                Statement statement = connection.createStatement();
                statement.execute("DELETE FROM " + tableName);
            } else {
                // Table does not exist, execute creation statement
                Statement statement = connection.createStatement();
                statement.execute(sqlCreateStatement);
            }
        } catch (SQLException e) {
            System.out.println("Table creation failed for " + tableName + ". Check output console.");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Initialize Location Table
     */
    private void initializeLocationTable() {
        initializeTable(
                "LOCATION",
                "CREATE TABLE LOCATION(nodeID char(16), xcoord int, ycoord int, floor char(4), building char(16), nodeType char(4), longName char(64), shortName char(32), Constraint nodeID_PK Primary Key (nodeID))"
        );
    }
}
