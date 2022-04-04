package edu.wpi.cs3733.D22.teamC.entity.medical_equipment;

import java.util.List;

public interface MedicalEquipmentDAO {
    public List<MedicalEquipment> getMedicalEquipments();
    public MedicalEquipment getMedicalEquipment(int equipID);

    public int insertMedicalEquipment(MedicalEquipment medical_equipment);
    public boolean updateMedicalEquipment(MedicalEquipment medical_equipment);
    public boolean deleteMedicalEquipment(MedicalEquipment medical_equipment);


}
