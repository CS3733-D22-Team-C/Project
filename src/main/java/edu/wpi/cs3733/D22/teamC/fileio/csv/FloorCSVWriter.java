package edu.wpi.cs3733.D22.teamC.fileio.csv;

import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;

public class FloorCSVWriter extends CSVWriter<Floor> {
    /**
     * Manually define headers of attributes output to CSV.
     * @return The array of headers to be output to CSV.
     */
    @Override
    protected String[] compileHeaders() {
        return new String[]{
                "id",
                "order",
                "longName",
                "shortName"
        };
    }

    /**
     * Maps headers to a value to get from the object.
     * @param object The object to be read from.
     * @param header The header to be mapped to an attribute.
     * @return The retrieved value to be output to the CSV.
     */
    @Override
    protected String compileAttribute(Floor object, String header) {
        String output = "";
        switch (header) {
            case "id":
                output = Integer.toString(object.getFloorID());
                break;
            case "order":
                output = Integer.toString(object.getOrder());
                break;
            case "longName":
                output = object.getLongName();
                break;
            case "shortName":
                output = object.getShortName();
                break;
            default:
                break;
        }
        return output;
    }
}
