package edu.wpi.cs3733.D22.teamC.entity.location;

import edu.wpi.cs3733.D22.teamC.DBManager;

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
    public Location getLocation(String nodeID) {
        try {
            // Execute SELECT Query
            PreparedStatement statement =  DBManager.getInstance().connection.prepareStatement(
                    "SELECT * FROM LOCATION WHERE NODEID = ?"
            );
            statement.setString(1, nodeID);
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
     * Update entry in LOCATION Table of DB corresponding to the given Location object.
     * @param location The Location whose corresponding DB entry is to be updated.
     */
    @Override
    public void updateLocation(Location location) {
        try {
            // TODO: Update Request !!!
        } catch (SQLException e) {
            System.out.println("Update to LOCATION table failed.");
            e.printStackTrace();
        }
    }

    /**
     * Delete entry in LOCATION Table of DB corresponding to the given Location object.
     * @param location The Location whose corresponding DB entry is to be deleted.
     */
    @Override
    public void deleteLocation(Location location) {
        try {
            // Execute DELETE Statement
            PreparedStatement statement =  DBManager.getInstance().connection.prepareStatement(
                    "DELETE FROM LOCATION WHERE NODEID = ?"
            );
            statement.setString(1, location.getNodeID());
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Delete from LOCATION table failed.");
            e.printStackTrace();
        }
    }

    /**
     * Create Location object from query resultSet.
     * @param resultSet ResultSet from query to Location DB Table.
     * @return Location object converted from query.
     */
    private Location createLocation(ResultSet resultSet) {
        try {
            Location location = new Location();

            location.setNodeID(resultSet.getString("NODEID"));
            location.setFloor(resultSet.getString("FLOOR"));
            location.setBuilding(resultSet.getString("BUILDING"));
            location.setNodeType(resultSet.getString("NODETYPE"));
            location.setLongName(resultSet.getString("LONGNAME"));
            location.setShortName(resultSet.getString("SHORTNAME"));
            location.setX(resultSet.getInt("XCOORD"));
            location.setY(resultSet.getInt("YCOORD"));

            return location;
        } catch (SQLException e) {
            System.out.println("Creation of object from LOCATION ResultSet failed.");
            e.printStackTrace();

            return null;
        }
    }
}
