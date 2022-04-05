package edu.wpi.cs3733.D22.teamC.entity.medical_equipment;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class MedicalEquipmentDAOImpl implements MedicalEquipmentDAO {
    /**
     * executing a SQL query from medical_equipment table to get the list of medical equipments in the current DB
     * @return the List of Medical Equipments in the current DB
     */
    @Override
    public List<MedicalEquipment> getMedicalEquipments() {
        try {
            Statement selectStatement = DBManager.getInstance().connection.createStatement();
            ResultSet resultSet = selectStatement.executeQuery("SELECT * FROM Medical_Equipment");

            List<MedicalEquipment> equipments = new ArrayList<>();
            while (resultSet.next()) {
                MedicalEquipment equipment = createEquipment(resultSet);
                if (equipment != null) equipments.add(equipment);
            }
            return equipments;
        }
        catch(SQLException e) {
            System.out.println("Query to Medical_Equipment table failed.");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * takes in a location and returns the list of medical equipments at this current location
     * @param locationID the id of the location given
     * @return the list of medical equipments at this current location
     */
    @Override
    public List<MedicalEquipment> getMedicalEquipmentAtLocation(int locationID) {
        try {
            // Execute SELECT Query
            PreparedStatement statement =  DBManager.getInstance().connection.prepareStatement(
                    "SELECT * FROM Medical_Equipment WHERE locationID = ?"
            );
            statement.setInt(1, locationID);
            ResultSet resultSet = statement.executeQuery();

            List<MedicalEquipment> equipments = new ArrayList<>();
            while (resultSet.next()) {
                MedicalEquipment equipment = createEquipment(resultSet);
                if(equipment != null) equipments.add(equipment);
            }
            return equipments;

        } catch (SQLException e) {
            System.out.println("Query to Medical_Equipment table failed.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * takes in the ID and returns the medical equipment associated with it
     * @param equipID the ID
     * @return the medical equipment with the given ID
     */
    @Override
    public MedicalEquipment getMedicalEquipment(int equipID) {
        try {
            // Execute SELECT Query
            PreparedStatement statement =  DBManager.getInstance().connection.prepareStatement(
                    "SELECT * FROM Medical_Equipment WHERE ID = ?"
            );
            statement.setInt(1, equipID);
            ResultSet resultSet = statement.executeQuery();

            // Return Location Object
            if (resultSet.next()) return createEquipment(resultSet);
        } catch (SQLException e) {
            System.out.println("Query to Medical_Equipment table failed.");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * inserts the given medical equipment into the DB
     * @param medical_equipment the equipment ready to be inserted
     * @return an integer signifying the insertion of the medical equipment into the table
     */
    @Override
    public int insertMedicalEquipment(MedicalEquipment medical_equipment) {
        try {
            // Check if entry of same nodeID already exists
            MedicalEquipment equipmentInDB = getMedicalEquipment(medical_equipment.getEquipID());
            if (equipmentInDB == null) {
                // Execute INSERT Statement
                PreparedStatement statement = (medical_equipment.getEquipID() == 0)
                        ? DBManager.getInstance().connection.prepareStatement("INSERT INTO MEDICAL_EQUIPMENT VALUES(DEFAULT, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)
                        : DBManager.getInstance().connection.prepareStatement("INSERT INTO MEDICAL_EQUIPMENT VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

                int index = 1;
                if (medical_equipment.getEquipID() != 0) {
                    statement.setInt(index, medical_equipment.getEquipID());
                    index++;
                }
                statement.setInt(index, medical_equipment.getLocationID()); index++;
                statement.setString(index, medical_equipment.getEquipmentType().toString()); index++;
                statement.setString(index, medical_equipment.getEquipmentStatus().toString());
                statement.execute();

                ResultSet resultSetID = statement.getGeneratedKeys();
                if(resultSetID.next()) return resultSetID.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("INSERT to Medical_Equipment table failed.");
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * updating the current medical_equipment of its values
     * @param medical_equipment the equipment ready to be inserted
     * @return a boolean signifying the update into table (true if updated successfully)
     */
    @Override
    public boolean updateMedicalEquipment(MedicalEquipment medical_equipment) {
        try {
            // Check if entry of same nodeID exists
            MedicalEquipment equipmentInDB = getMedicalEquipment(medical_equipment.getEquipID());
            if (equipmentInDB != null) {
                // Execute UPDATE Statement
                PreparedStatement statement =  DBManager.getInstance().connection.prepareStatement(
                        "UPDATE Medical_Equipment SET locationID = ?, EquipType = ?, EquipStatus = ?" +
                                " WHERE ID = ?"
                );
                statement.setInt(1, medical_equipment.getLocationID());
                statement.setString(2, medical_equipment.getEquipmentType().toString());
                statement.setString(3, medical_equipment.getEquipmentStatus().toString());
                statement.setInt(4, medical_equipment.getEquipID());
                statement.execute();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Update to Medical_Equipment table failed.");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * deleting the given medical_equipment from the table
     * @param medical_equipment the equipment ready to be deleted
     * @return a boolean signifying the deletion of the equipment from the table (true if deleted successfully)
     */
    @Override
    public boolean deleteMedicalEquipment(MedicalEquipment medical_equipment) {
        try {
            // Check if entry of same nodeID exists
            MedicalEquipment equipmentInDB = getMedicalEquipment(medical_equipment.getEquipID());
            if (equipmentInDB != null) {
                // Execute UPDATE Statement
                PreparedStatement statement =  DBManager.getInstance().connection.prepareStatement(
                        "DELETE FROM Medical_Equipment" +
                                " WHERE ID = ?"
                );
                statement.setInt(1, medical_equipment.getEquipID());
                statement.execute();

                return true;
            }
        } catch (SQLException e) {
            System.out.println("Update to Medical_Equipment table failed.");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * creates the medical equipment
     * @param resultSet the resultSet from query to Medical_Equipment DB Table
     * @return the medical equipment object created from the query.
     */
    private MedicalEquipment createEquipment(ResultSet resultSet) {
        try {
            MedicalEquipment equipment = new MedicalEquipment(resultSet.getInt("ID"));
            equipment.setLocationID(resultSet.getInt("LOCATIONID"));
            equipment.setEquipmentType(MedicalEquipment.EquipmentType.valueOf(resultSet.getString("EQUIPTYPE")));
            equipment.setEquipmentStatus(MedicalEquipment.EquipmentStatus.valueOf(resultSet.getString("EQUIPSTATUS")));
            return equipment;
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
