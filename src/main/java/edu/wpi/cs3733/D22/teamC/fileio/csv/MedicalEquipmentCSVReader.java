package edu.wpi.cs3733.D22.teamC.fileio.csv;

import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;

import java.sql.Timestamp;

public class MedicalEquipmentCSVReader extends CSVReader<MedicalEquipment>{
    /**
     * Maps ServiceRequest (header, value) pairs to a value to change for the object.
     * @param medicalEquipment The object to be modified.
     * @param header The header to be mapped to an attribute.
     * @param value The value for the current attribute.
     * @return The Location modified with the value at the corresponding attribute.
     */
    @Override
    protected MedicalEquipment parseAttribute(MedicalEquipment medicalEquipment, String header, String value){
        switch(header) {
            case "ID":
                medicalEquipment.setEquipID(value);
                break;
            case "locationID":
                medicalEquipment.setLocationID(value);
                break;
            case "equipType":
                medicalEquipment.setEquipmentType(MedicalEquipment.EquipmentType.valueOf(value));
                break;
            case "typeNumber":
                medicalEquipment.setTypeNumber(Integer.parseInt(value));
                break;
            case "status":
                medicalEquipment.setStatus(MedicalEquipment.EquipmentStatus.valueOf(value));
                break;
            default:
                break;
        }
        return medicalEquipment;
    }

    @Override
    protected MedicalEquipment createObject(){ return new MedicalEquipment();}
}
