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
            initializeLabSystemSRTable(clearTable);
            initializeSecuritySRTable(clearTable);
        }

        /**
         * Initialize Location Table.
         * @param clearTable Clear pre-existing table entries if true.
         */
        public void initializeLocationTable(boolean clearTable) {
            initializeTable(
                    "LOCATION",
                    "CREATE TABLE LOCATION (" +
                            "ID int NOT NULL GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1), " +
                            "XCoord int, YCoord int, Floor char(4), Building char(16), NodeType char(4), " +
                            "LongName char(64), ShortName char(32), CONSTRAINT pk_ID PRIMARY KEY (ID))",
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
                    "CREATE TABLE SERVICE_REQUEST (" +
                            "ID int NOT NULL GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1), " +
                            "CreatorID char(10), AssigneeID char(10), LocationID char(10), " +
                            "CreationTimestamp timestamp, Status varchar(50), Priority varchar(50), " +
                            "RequestType varchar(50), Description varchar(150), ModifierID char(10), " +
                            "ModifiedTimestamp timestamp," +
                            "CONSTRAINT pk_SRID PRIMARY KEY (ID))",
                    clearTable
            );
        }

    /**
     * Initialize Medical Equipment Service Request Table.
     * @param clearTable Clear pre-existing table entries if true.
     */
    public void initializeMedicalEquipSRTable(boolean clearTable) {
        initializeTable(
                "MEDICAL_EQUIPMENT_SR",
                "CREATE TABLE MEDICAL_EQUIPMENT_SR (" +
                        "ID int, EquipID char(10), EquipType varchar(50), CONSTRAINT fk_MESRID FOREIGN KEY (ID) " +
                        "REFERENCES SERVICE_REQUEST (ID) ON DELETE CASCADE)",
                clearTable
        );
    }
    
    /**
     * Initialize Lab System Service Request Table.
     * @param clearTable Clear pre-existing table entries if true.
     */
    public void initializeLabSystemSRTable(boolean clearTable) {
        initializeTable(
                "LAB_SYSTEM_SR",
                "CREATE TABLE LAB_SYSTEM_SR (" +
                        "ID int, LabType varchar(50), PatientID char(10), CONSTRAINT fk_LSSRID FOREIGN KEY (ID) " +
                        "REFERENCES SERVICE_REQUEST (ID) ON DELETE CASCADE)",
                clearTable
        );
    }
    
    /**
     * Initialize Security Service Request Table.
     * @param clearTable Clear pre-existing table entries if true.
     */
    public void initializeSecuritySRTable(boolean clearTable) {
        initializeTable(
                "SECURITY_SR",
                "CREATE TABLE SECURITY_SR (" +
                        "ID int, SecurityType varchar(50), CONSTRAINT fk_SSRID FOREIGN KEY (ID) " +
                        "REFERENCES SERVICE_REQUEST (ID) ON DELETE CASCADE)",
                clearTable
        );
    }
    //endregion
}
