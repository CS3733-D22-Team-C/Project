package edu.wpi.cs3733.D22.teamC.entity.medical_equipment;

import java.util.List;

public interface Medical_EquipmentDAO {
    public List<Medical_Equipment> getMedicalEquipments();
    public Medical_Equipment getMedicalEquipment(int equipID);

    public int insertMedicalEquipment(Medical_Equipment medical_equipment);
    public boolean updateMedicalEquipment(Medical_Equipment medical_equipment);
    public boolean deleteMedicalEquipment(Medical_Equipment medical_equipment);


}
