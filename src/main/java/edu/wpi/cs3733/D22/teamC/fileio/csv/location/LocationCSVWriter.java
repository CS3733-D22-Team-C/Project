package edu.wpi.cs3733.D22.teamC.fileio.csv.location;

import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.fileio.csv.CSVWriter;

public class LocationCSVWriter extends CSVWriter<Location> {
    /**
     * Manually define headers of attributes output to CSV.
     * @return The array of headers to be output to CSV.
     */
    @Override
    protected String[] compileHeaders() {
        return new String[]{
                "nodeID",
                "xcoord",
                "ycoord",
                "floor",
                "building",
                "nodeType",
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
    protected String compileAttribute(Location object, String header) {
        String output = "";
        switch (header) {
            case "nodeID":
                output = object.getID();
                break;
            case "xcoord":
                output = Integer.toString(object.getX());
                break;
            case "ycoord":
                output = Integer.toString(object.getY());
                break;
            case "floor":
                output = object.getFloor();
                break;
            case "building":
                output = object.getBuilding();
                break;
            case "nodeType":
                output = object.getNodeType().toString();
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
