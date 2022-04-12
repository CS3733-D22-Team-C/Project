package edu.wpi.cs3733.D22.teamC.fileio.csv;

import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;

public class MedicalEquipmentCSVWriter extends CSVWriter<MedicalEquipment> {
    /**
     * Manually define medical equipment attributes to CSV
     * @return array of headers to CSV
     */
    @Override
    protected String[] compileHeaders() {
        return new String[] {
                "ID",
                "locationID",
                "typeNumber",
                "equipType",
                "equipStatus"
        };
    }

    /**
     * Maps header to a value get from the object
     * @param object The object to be read from.
     * @param header The header to be mapped to an attribute.
     * @return The retrieved value to be output to the CSV.
     */
    @Override
    protected String compileAttribute(MedicalEquipment object, String header) {
        String output = "";
        switch(header) {
            case "ID":
                output = object.getEquipID();
                break;
            case "locationID":
                output = object.getLocationID();
                break;
            case "equipType":
                output = object.getEquipmentType().toString();
                break;
            case "equipStatus":
                output = object.getStatus().toString();
                break;
        }
        return output;
    }
}
