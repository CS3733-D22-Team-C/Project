package edu.wpi.cs3733.D22.teamC;

import java.sql.*;

/**
 * DBManager serves as manager class for interacting with the Database.
 */
public class DBManager {
    // Singleton Instance
    private static DBManager instance;

    // Database Connection
    public Connection connection;

    /**
     * Get instance of DB Manager according to the Singleton Model
     */
    public static DBManager getInstance(){
        if (instance == null) {
            instance = new DBManager();
            instance.connectDatabase();
            instance.initializeLocationTable(false);
        }
        return instance;
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
     * Checks if a Table of the given name exists. If it does and emptyTable is true, clear its contents.
     * If it doesn't, execute the CREATE statement.
     * @param tableName Name of table to initialize.
     * @param sqlCreateStatement SQL CREATE Statement to run in case of non-existence.
     * @param emptyTable Clear pre-existing table entries if true.
     */
    private void initializeTable(String tableName, String sqlCreateStatement, boolean emptyTable) {
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(null, null, tableName, new String[] {"TABLE"});

            if (resultSet.next()) {
                // Table exists, clear data from table if emptyTable
                if (emptyTable) {
                    Statement statement = connection.createStatement();
                    statement.execute("DELETE FROM " + tableName);
                }
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
     * @param emptyTable Clear pre-existing table entries if true.
     */
    private void initializeLocationTable(boolean emptyTable) {
        initializeTable(
                "LOCATION",
                "CREATE TABLE LOCATION(NODEID char(16), XCOORD int, YCOORD int, FLOOR char(4), BUILDING char(16), NODETYPE char(4), LONGNAME char(64), SHORTNAME char(32), Constraint nodeID_PK Primary Key (nodeID))",
                emptyTable
        );
    }
}
