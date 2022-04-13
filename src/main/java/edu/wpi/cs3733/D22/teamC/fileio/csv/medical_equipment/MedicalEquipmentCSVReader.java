package edu.wpi.cs3733.D22.teamC.fileio.csv.medical_equipment;

import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.fileio.csv.CSVReader;

public class MedicalEquipmentCSVReader extends CSVReader<MedicalEquipment> {
    /**
     * Maps Medical Equipment (header, value) to a value to change for the object
     * @param object The object to be modified.
     * @param header The header to be mapped to an attribute.
     * @param value The value for the current attribute.
     * @return The medical equipment modified with the value at corresponding attribute
     */
    @Override
    protected MedicalEquipment parseAttribute(MedicalEquipment object, String header, String value) {
        switch(header) {
            case "ID":
                object.setEquipID(value);
                break;
            case "locationID":
                object.setLocationID(value);
                break;
            case "typeNumber":
                if(!value.equals("")){
                    object.setTypeNumber(Integer.parseInt(value));
                }
                break;
            case "equipType":
                object.setEquipmentType(MedicalEquipment.EquipmentType.valueOf(value));
                break;
            case "equipStatus":
                object.setStatus(MedicalEquipment.EquipmentStatus.valueOf(value));
                break;
        }
        return object;
    }

    /**
     * wrapper for medical equipment constructor
     * @return Newly created medical equipment object
     */
    @Override
    protected MedicalEquipment createObject() {
        return new MedicalEquipment();
    }
}
