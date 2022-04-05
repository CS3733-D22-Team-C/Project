package edu.wpi.cs3733.D22.teamC.entity.location;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAOImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO Implementing class for interfacing with the Location DB Table.
 */
public class LocationDAOImpl implements LocationDAO {
    /**
     * Get all entries in LOCATION Table of DB, converting them to Location objects.
     * @return List of all Location objects converted from queries.
     */
    @Override
    public List<Location> getAllLocations() {
        try {
            // Execute SELECT Query
            Statement selectStatement = DBManager.getInstance().connection.createStatement();
            ResultSet resultSet = selectStatement.executeQuery("SELECT * FROM LOCATION");

            // Return Location Objects
            List<Location> locations = new ArrayList<>();
            while (resultSet.next()) {
                Location location = createLocation(resultSet);
                if (location != null) locations.add(location);
            }
            return locations;
        } catch (SQLException e) {
            System.out.println("Query to LOCATION table failed.");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get entry in LOCATION Table of DB with given nodeID, converting it to a Location object.
     * @param nodeID The given nodeID.
     * @return Location object converted from query.
     */
    @Override
    public Location getLocation(int nodeID) {
        try {
            // Execute SELECT Query
            PreparedStatement statement =  DBManager.getInstance().connection.prepareStatement(
                    "SELECT * FROM LOCATION WHERE ID = ?"
            );
            statement.setInt(1, nodeID);
            ResultSet resultSet = statement.executeQuery();

            // Return Location Object
            if (resultSet.next()) return createLocation(resultSet);
        } catch (SQLException e) {
            System.out.println("Query to LOCATION table failed.");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Insert entry in LOCATION Table of DB corresponding to the given Location object.
     * @param location The Location to be inserted into the DB via a corresponding entry.
     * @return If successful return true, else return false.
     */
    @Override
    public int insertLocation(Location location) {
        try {
            // Check if entry of same nodeID already exists
            Location locationInDB = getLocation(location.getNodeID());
            Floor floor = (new FloorDAOImpl()).getFloor(location.getFloor());
            if (locationInDB == null && floor != null) {
                // Execute INSERT Statement
                PreparedStatement statement = (location.getNodeID() == 0)
                        ? DBManager.getInstance().connection.prepareStatement("INSERT INTO LOCATION VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)
                        : DBManager.getInstance().connection.prepareStatement("INSERT INTO LOCATION VALUES(?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

                int index = 1;
                if (location.getNodeID() != 0) {
                    statement.setString(index, String.valueOf(location.getNodeID()));
                    index++;
                }

                statement.setInt(index, location.getX()); index++;
                statement.setInt(index, location.getY()); index++;
                statement.setInt(index, location.getFloor()); index++;
                statement.setString(index, location.getBuilding()); index++;
                statement.setString(index, location.getNodeType().toString()); index++;
                statement.setString(index, location.getLongName()); index++;
                statement.setString(index, location.getShortName());
                statement.execute();

                ResultSet resultSetID = statement.getGeneratedKeys();
                if(resultSetID.next()) return resultSetID.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("INSERT to LOCATION table failed.");
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * Update entry in LOCATION Table of DB corresponding to the given Location object.
     * @param location The Location whose corresponding DB entry is to be updated.
     * @return If successful return true, else return false.
     */
    @Override
    public boolean updateLocation(Location location) {
        try {
            // Check if entry of same nodeID exists
            Location locationInDB = getLocation(location.getNodeID());
            if (locationInDB != null) {
                // Execute UPDATE Statement
                PreparedStatement statement =  DBManager.getInstance().connection.prepareStatement(
                        "UPDATE LOCATION SET XCOORD = ?, YCOORD = ?, FLOOR = ?, BUILDING = ?, " +
                                "NODETYPE = ?, LONGNAME = ?, SHORTNAME = ? WHERE ID = ?"
                );
                statement.setInt(1, location.getX());
                statement.setInt(2, location.getY());
                statement.setInt(3, location.getFloor());
                statement.setString(4, location.getBuilding());
                statement.setString(5, location.getNodeType().toString());
                statement.setString(6, location.getLongName());
                statement.setString(7, location.getShortName());
                statement.setInt(8, location.getNodeID());
                statement.execute();

                return true;
            }
        } catch (SQLException e) {
            System.out.println("Update to LOCATION table failed.");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Delete entry in LOCATION Table of DB corresponding to the given Location object.
     * @param location The Location whose corresponding DB entry is to be deleted.
     * @return If successful return true, else return false.
     */
    @Override
    public boolean deleteLocation(Location location) {
        try {
            // Check if entry of same nodeID exists
            Location locationInDB = getLocation(location.getNodeID());
            if (locationInDB != null) {
                // Execute DELETE Statement
                PreparedStatement statement =  DBManager.getInstance().connection.prepareStatement(
                        "DELETE FROM LOCATION WHERE ID = ?"
                );
                statement.setInt(1, location.getNodeID());
                statement.execute();

                return true;
            }
        } catch (SQLException e) {
            System.out.println("Delete from LOCATION table failed.");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Create Location object from query resultSet.
     * @param resultSet ResultSet from query to Location DB Table.
     * @return Location object converted from query.
     */
    public Location createLocation(ResultSet resultSet) {
        try {
            Location location = new Location(resultSet.getInt("ID"));
            location.setFloor(resultSet.getInt("FLOOR"));
            location.setBuilding(typesafeTrim(resultSet.getString("BUILDING")));
            location.setNodeType(Location.NodeType.valueOf(resultSet.getString("NODETYPE")));
            location.setLongName(typesafeTrim(resultSet.getString("LONGNAME")));
            location.setShortName(typesafeTrim(resultSet.getString("SHORTNAME")));
            location.setX(resultSet.getInt("XCOORD"));
            location.setY(resultSet.getInt("YCOORD"));

            return location;
        } catch (SQLException e) {
            System.out.println("Creation of object from LOCATION ResultSet failed.");
            e.printStackTrace();

            return null;
        }
    }

    /**
     * Trim str if not null.
     * @param str The String to trim.
     * @return The trimmed str.
     */
    private String typesafeTrim(String str) {
        if (str == null) return null;
        else return str.trim();
    }
}
