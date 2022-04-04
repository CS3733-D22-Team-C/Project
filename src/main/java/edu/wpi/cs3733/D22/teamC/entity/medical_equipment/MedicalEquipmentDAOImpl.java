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


    @Override
    public List<MedicalEquipment> getMedicalEquipmentAtLocation(Location location) {
        try {
            // Execute SELECT Query
            PreparedStatement statement =  DBManager.getInstance().connection.prepareStatement(
                    "SELECT * FROM Medical_Equipment WHERE locationID = ?"
            );
            statement.setInt(1, location.getNodeID());
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

    @Override
    public int insertMedicalEquipment(MedicalEquipment medical_equipment) {
        return 0;
    }

    @Override
    public boolean updateMedicalEquipment(MedicalEquipment medical_equipment) {
        return false;
    }

    @Override
    public boolean deleteMedicalEquipment(MedicalEquipment medical_equipment) {
        return false;
    }

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
