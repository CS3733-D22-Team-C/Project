package edu.wpi.cs3733.D22.teamC;

import java.sql.*;

/**
 * DBManager serves as manager class for interacting with the Database.
 */
public class DBManager {
    // Constants
    public static final String DEVELOPMENT_DATABASE_NAME = "DB";
    public static final String TESTING_DATABASE_NAME = "TESTDB";

    // Singleton Instance
    private static DBManager instance;

    // Variables
    private String databaseName;

    // References
    public Connection connection;

    /**
     * Privatized Constructor.
     * @param dbName Name of Database to connect to.
     */
    private DBManager(String dbName) {
        this.databaseName = dbName;
    };

    //region Singleton Code
        /**
         * Get instance of DB Manager according to the Singleton Model.
         * @return Singleton instance of DB Manager.
         */
        public static DBManager getInstance() {
            if (instance == null) startup(DEVELOPMENT_DATABASE_NAME);
            return instance;
        }

        /**
         * Startup singleton instance of DB Manager.
         * @param dbName Name of DB to connect to.
         * @return Singleton instance of DB Manager.
         */
        public static synchronized DBManager startup(String dbName) {
            if (instance != null) shutdown();

            instance = new DBManager(dbName);
            instance.connectDatabase();
            return instance;
        }

        /**
         * Shutdown singleton instance of DB Manager.
         */
        public static synchronized void shutdown() {
            if (instance != null) {
                instance.disconnectDatabase();
                instance = null;
            }
        }
    //endregion

    //region Database Connection
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
    //endregion

    //region Table Initialization
        /**
         * Checks if a Table of the given name exists. If it does and emptyTable is true, clear its contents.
         * If it doesn't, execute the CREATE statement.
         * @param tableName Name of table to initialize.
         * @param sqlCreateStatement SQL CREATE Statement to run in case of non-existence.
         * @param clearTable Clear pre-existing table entries if true.
         */
        public void initializeTable(String tableName, String sqlCreateStatement, boolean clearTable) {
            try {
                DatabaseMetaData databaseMetaData = connection.getMetaData();
                ResultSet resultSet = databaseMetaData.getTables(null, null, tableName, new String[] {"TABLE"});

                if (resultSet.next()) {
                    // Table exists, clear data from table if emptyTable
                    if (clearTable) {
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
         * Initialize all Tables.
         * @param clearTable Clear pre-existing table entries for all tables if true.
         */
        public void initializeTables(boolean clearTable) {
            initializeLocationTable(clearTable);
            initializeServiceRequestTable(clearTable);
            initializeMedicalEquipSRTable(clearTable);
        }

        /**
         * Initialize Location Table.
         * @param clearTable Clear pre-existing table entries if true.
         */
        public void initializeLocationTable(boolean clearTable) {
            initializeTable(
                    "LOCATION",
                    "CREATE TABLE LOCATION(NODEID char(16), XCOORD int, YCOORD int, FLOOR char(4), " +
                            "BUILDING char(16), NODETYPE char(4), LONGNAME char(64), SHORTNAME char(32), " +
                            "Constraint nodeID_PK Primary Key (nodeID))",
                    clearTable
            );
        }
        /**
         * Initialize Service Request Table.
         * @param clearTable Clear pre-existing table entries if true.
         */
        public void initializeServiceRequestTable(boolean clearTable) {
            initializeTable(
                    "SERVICE_REQUEST",
                    "CREATE TABLE SERVICE_REQUEST (REQUESTID char(10), CREATORID char(10), " +
                            "ASSIGNEEID char(10), LOCATIONID char(10), CREATIONTIMESTAMP timestamp, STATUS varchar(50), " +
                            "PRIORITY varchar(50), REQUESTTYPE varchar(50), DESCRIPTION varchar(150), " +
                            "CONSTRAINT PK_REQUESTID PRIMARY KEY (REQUESTID))",
                    clearTable
            );
        }

        /**
         * Initialize Medical Equipment Service Request Table.
         * @param clearTable Clear pre-existing table entries if true.
         */
        public void initializeMedicalEquipSRTable(boolean clearTable) {
            initializeTable(
                    "MEDICAL_EQUIP_SERVICE_REQUEST",
                    "CREATE TABLE MEDICAL_EQUIP_SERVICE_REQUEST (REQUESTID char(10), EQUIPID char(10), " +
                            "EQUIPTYPE varchar(50), CONSTRAINT fk_requestID FOREIGN KEY (REQUESTID) " +
                            "REFERENCES SERVICE_REQUEST (REQUESTID) ON DELETE CASCADE)",
                    clearTable
            );
        }
    //endregion
}
