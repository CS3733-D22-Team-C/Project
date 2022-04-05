package edu.wpi.cs3733.D22.teamC.entity.floor;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FloorDAOImpl implements FloorDAO {
    LocationDAOImpl locationDAO = new LocationDAOImpl();



    @Override
    public List<Location> getAllLocations(int floor) {
        try {
            PreparedStatement selectStatement = DBManager.getInstance().connection.prepareStatement(
                    "SELECT * FROM LOCATION WHERE Floor = ?");
            selectStatement.setInt(1, floor);
            ResultSet resultSet = selectStatement.executeQuery();


            List<Location> floorList = new ArrayList<>();

            while(resultSet.next()){
                Location foundLocation = locationDAO.createLocation(resultSet);
                if(foundLocation != null){
                    floorList.add(foundLocation);
                }
            }
            return floorList;
        } catch
        (SQLException e) {
            System.out.println("Query to FLOOR table failed.");
            e.printStackTrace();
        }
        return null;

    }


    @Override
    public int insertFloor(Floor floor){
        try{
            // check if the entry of the same floorID already exits
            Floor floorInDB = getFloor(floor.getFloorID());
            if(floorInDB == null){
                // execute INSERT statement
                PreparedStatement statement = (floor.getFloorID()==0)
                        ? DBManager.getInstance().connection.prepareStatement("INSERT INTO FLOOR VALUES (DEFAULT,?,?,?)", Statement.RETURN_GENERATED_KEYS)
                        : DBManager.getInstance().connection.prepareStatement("INSERT INTO FLOOR VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

                int index = 1;

                if(floor.getFloorID() != 0){
                    statement.setString(index, String.valueOf(floor.getFloorID()));
                    index++;
                }
                statement.setInt(index, floor.getOrder()); index++;
                statement.setString(index, floor.getLongName()); index++;
                statement.setString(index, floor.getShortName());
                statement.execute();

                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()) return resultSet.getInt(1);

            }

        }catch(SQLException e){
            System.out.println("INSERT to FLOOR table failed.");
            e.printStackTrace();
        }
        return -1;

    }

    @Override
    public boolean updateFloor(Floor floor) {
        try {
            //check if entry of the same employeeID exits
            Floor floorInDB = getFloor(floor.getFloorID());

            if(floorInDB != null){
                PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                        "UPDATE FLOOR SET FloorOrder=?, LongName=?, ShortName=? WHERE ID=?"
                );

                statement.setInt(1,floor.getOrder());
                statement.setString(2,floor.getLongName());
                statement.setString(3,floor.getShortName());
                statement.setInt(4, floor.getFloorID());
                statement.execute();

                return true;
            }
        }catch(SQLException E){
            System.out.println("Update to the FLOOR table failed.");
            E.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteFloor(Floor floor) {
        try{
            Floor floorInDB = getFloor(floor.getFloorID());
            if(floorInDB != null){

                List<Location> locationsToDelete = getAllLocations(floor.getFloorID());
                for(Location location :locationsToDelete){
                    locationDAO.deleteLocation(location);
                }

                PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                        "DELETE FROM FLOOR WHERE ID = ?"
                );
                statement.setInt(1,floor.getFloorID());
                statement.execute();

                return true;
            }

        }catch(SQLException e){
            System.out.println("DELETE from FLOOR table failed.");
            e.printStackTrace();
        }
        return false;


    }

    public Floor createFloor(ResultSet resultset) {
        try {
            Floor floor = new Floor(resultset.getInt("ID"));
            floor.setOrder(resultset.getInt("FloorOrder"));
            floor.setLongName(typesafeTrim(resultset.getString("LongName")));
            floor.setShortName(typesafeTrim(resultset.getString("ShortName")));

            return floor;

        } catch (SQLException e) {
            System.out.println("Creation of object from FLOOR ResultSet failed.");
            e.printStackTrace();

            return null;
        }
    }
    @Override
    public Floor getFloor(int floorID){
        try{
            PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                    "SELECT * FROM FLOOR WHERE ID = ?"
            );
            statement.setInt(1, floorID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return createFloor(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Query to getFloor from FLOOR table failed");
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Floor> getAllFloors() {
        try {
            Statement selectStatement = DBManager.getInstance().connection.createStatement();
            ResultSet resultSet = selectStatement.executeQuery("SELECT * FROM FLOOR");
            List<Floor> floorList = new ArrayList<>();

            while(resultSet.next()){
                Floor floor = createFloor(resultSet);
                if(floor != null){
                    floorList.add(floor);
                }
            }
            return floorList;
        } catch
        (SQLException e) {
            System.out.println("Query to FLOOR table failed.");
            e.printStackTrace();
        }
        return null;
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




