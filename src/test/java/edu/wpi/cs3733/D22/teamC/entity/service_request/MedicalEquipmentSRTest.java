package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAOTest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAO;
import edu.wpi.cs3733.D22.teamC.factory.Factory;
import edu.wpi.cs3733.D22.teamC.factory.service_request.MedicalEquipmentSRFactory;

public class MedicalEquipmentSRTest extends DAOTest<MedicalEquipmentSR> {
    public MedicalEquipmentSRTest() {
        super(new MedicalEquipmentSRFactory(), new MedicalEquipmentSRDAO());
    }
}
