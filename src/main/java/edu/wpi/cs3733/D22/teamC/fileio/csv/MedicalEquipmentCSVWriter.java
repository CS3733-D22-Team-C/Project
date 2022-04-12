package edu.wpi.cs3733.D22.teamC.fileio.csv;

import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;

public class MedicalEquipmentCSVWriter extends CSVWriter<MedicalEquipment> {
    /**
     * Manually define headers of attributes output to CSV.
     * @return The array of headers to be output to CSV.
     */
    @Override
    protected String[] compileHeaders() {
        return new String[]{
                "ID",
                "locationID",
                "equipType",
                "typeNumber",
                "status"
        };
    }
    /**
     * Maps headers to a value to get from the object.
     * @param medicalEquipment The object to be read from.
     * @param header The header to be mapped to an attribute.
     * @return The retrieved value to be output to the CSV.
     */
    @Override
    protected String compileAttribute(MedicalEquipment medicalEquipment, String header) {
        String output = "";
        switch (header) {
            case "ID":
                output = medicalEquipment.getEquipID();
                break;
            case "locationID":
                output = medicalEquipment.getLocationID();
                break;
            case "equipType":
                output = medicalEquipment.getEquipmentType().toString();
                break;
            case "typeNumber":
                output = Integer.toString(medicalEquipment.getTypeNumber());
                break;
            case "status":
                output = medicalEquipment.getStatus().toString();
                break;
            default:
                break;
        }
        return output;
    }
}
