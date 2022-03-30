package edu.wpi.cs3733.D22.teamC.fileio.csv;

import edu.wpi.cs3733.D22.teamC.entity.location.Location;

public class LocationCSVReader extends CSVReader<Location> {

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
                object.setNodeType(value);
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

    @Override
    protected Location createObject() {
        return new Location();
    }
}
