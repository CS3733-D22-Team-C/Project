package edu.wpi.cs3733.D22.teamC.fileio.csv.location;

import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.fileio.csv.CSVReader;

public class LocationCSVReader extends CSVReader<Location> {

    /**
     * Maps Location (header, value) pairs to a value to change for the object.
     * @param object The object to be modified.
     * @param header The header to be mapped to an attribute.
     * @param value The value for the current attribute.
     * @return The Location modified with the value at the corresponding attribute.
     */
    @Override
    protected Location parseAttribute(Location object, String header, String value) {
        switch (header) {
            case "nodeID":
                object.setNodeID(value);
                break;
            case "xcoord":
                object.setX(Integer.parseInt(value));
                break;
            case "ycoord":
                object.setY(Integer.parseInt(value));
                break;
            case "floor":
                object.setFloor(value);
                break;
            case "building":
                object.setBuilding(value);
                break;
            case "nodeType":
                object.setNodeType(Location.NodeType.valueOf(value));
                break;
            case "longName":
                object.setLongName(value);
                break;
            case "shortName":
                object.setShortName(value);
                break;
            default:
                break;
        }
        return object;
    }

    /**
     * Wrapper for Location constructor.
     * @return Newly created Location Object.
     */
    @Override
    protected Location createObject() {
        return new Location();
    }
}
