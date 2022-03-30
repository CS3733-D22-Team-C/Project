package edu.wpi.cs3733.D22.teamC;

import javafx.scene.layout.BorderPane;

import java.sql.*;

/**
 * DBManager serves as manager class for interacting with the Database.
 */
public class DBManager {
    // Constants
    public static final String DEVELOPMENT_DATABASE_NAME = "DB";

    // Singleton Instance
    private static DBManager instance;

    // Variables
    private String databaseName;
    private boolean emptyTables;

    // References
    public Connection connection;

    /**
     * Default Constructor.
     */
    public DBManager() {
        this.databaseName = DEVELOPMENT_DATABASE_NAME;
        this.emptyTables = true;
    }

    /**
     * Alternate Constructor
     * @param databaseName Name of database for this DB Manager to connect to.
     * @param emptyTables Whether this DB Manager should empty its Tables.
     */
    public DBManager(String databaseName, boolean emptyTables) {
        this.databaseName = databaseName;
        this.emptyTables = emptyTables;
    }

    /**
     * Get instance of DB Manager according to the Singleton Model.
     */
    public static DBManager getInstance() {
        return instance;
    }

    /**
     * Startup singleton instance of DB Manager.
     */
    public static void startup() {
        startup(new DBManager());
    }

    /**
     * Startup singleton instance of DB Manager with predefined DB Manager.
     * @param dbManager
     */
    public static void startup(DBManager dbManager) {
        if (instance != null) shutdown();

        instance = dbManager;
        instance.connectDatabase();
        instance.initializeLocationTable();
        instance.initializeSRTable();
        instance.initializeMedicalEquipSRTable();
    }

    /**
     * Shutdown singleton instance of DB Manager.
     */
    public static void shutdown() {
        if (instance != null) {
            instance.disconnectDatabase();
            instance = null;
        }
    }

    /**
     * Connect to database.
     */
    public void connectDatabase() {
        try {
            // Connect to DB
            connection = DriverManager.getConnection("jdbc:derby:" + databaseName + ";create=true;");
            Statement connectionStatement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("Apache Derby connection established!");
    }

    /**
     * End connection to database.
     */
    public void disconnectDatabase() {
        try {
            // Disconnect from DB
            connection.close();
        } catch (SQLException e) {
            System.out.println("Disconnecting failed. Check output console.");
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("Apache Derby connection disconnected!");
    }

    /**
     * Checks if a Table of the given name exists. If it does and emptyTable is true, clear its contents.
     * If it doesn't, execute the CREATE statement.
     * @param tableName Name of table to initialize.
     * @param sqlCreateStatement SQL CREATE Statement to run in case of non-existence.
     * @param emptyTable Clear pre-existing table entries if true.
     */
    public void initializeTable(String tableName, String sqlCreateStatement, boolean emptyTable) {
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
     */
    public void initializeLocationTable() {
        initializeTable(
                "LOCATION",
                "CREATE TABLE LOCATION(NODEID char(16), XCOORD int, YCOORD int, FLOOR char(4), BUILDING char(16), NODETYPE char(4), LONGNAME char(64), SHORTNAME char(32), Constraint nodeID_PK Primary Key (nodeID))",
                emptyTables
        );
    }
    /**
    Initialize Service Request Table
     */
    public void initializeSRTable() {
        initializeTable(
                "SERVICE_REQUEST",
                "CREATE TABLE SERVICE_REQUEST ( REQUESTID char(10), CREATORID char(10) , ASSIGNEEID char(10), LOCATIONID char(10), CREATIONTIMESTAMP timestamp, STATUS  varchar(50), PRIORITY varchar(50), REQUESTTYPE varchar(50), DESCRIPTION varchar(150), CONSTRAINT PK_REQUESTID PRIMARY KEY (REQUESTID))",
                         emptyTables
        );

    }

    /**
     * Initialize Medical Equipment Service Request Table
     */
    public void initializeMedicalEquipSRTable() {
        initializeTable("MEDICAL_EQUIP_SERVICE_REQUEST",
                "CREATE TABLE MEDICAL_EQUIP_SERVICE_REQUEST (FK_REQUESTID char(10), EQUIPID char(10), EQUIPTYPE varchar(50), FOREIGN KEY(FK_REQUESTID) REFERENCES SERVICE_REQUEST(REQUESTID))"
                ,emptyTables);
    }
}
