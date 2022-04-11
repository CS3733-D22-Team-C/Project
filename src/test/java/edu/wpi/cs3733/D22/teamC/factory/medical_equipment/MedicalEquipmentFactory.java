package edu.wpi.cs3733.D22.teamC.factory.medical_equipment;

import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.factory.Factory;

import java.util.Random;

public class MedicalEquipmentFactory implements Factory<MedicalEquipment> {
    private Random generator = new Random();

    public MedicalEquipment create() {
        MedicalEquipment equipment = new MedicalEquipment();

        equipment.setEquipmentType(MedicalEquipment.EquipmentType.values()[generator.nextInt(MedicalEquipment.EquipmentType.values().length)]);
        equipment.setStatus(MedicalEquipment.EquipmentStatus.values()[generator.nextInt(MedicalEquipment.EquipmentStatus.values().length)]);
        equipment.setTypeNumber(generator.nextInt(30));
        equipment.setLocationID("test");

        return equipment;
    }
}
