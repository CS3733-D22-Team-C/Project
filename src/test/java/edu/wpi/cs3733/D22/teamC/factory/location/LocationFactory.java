package edu.wpi.cs3733.D22.teamC.factory.location;

import edu.wpi.cs3733.D22.teamC.entity.location.Location;

import java.util.Random;

public class LocationFactory {
    private Random generator = new Random();

    public Location create() {
        Location location = new Location();

        int floor = generator.nextInt(200000);
        String building = "Wong Labs";
        Location.NodeType nodeType = Location.NodeType.values()[generator.nextInt(Location.NodeType.values().length)];
        String longName = "Cunning Chimera";
        String shortName = "CC1000";
        int x = generator.nextInt(10), y = generator.nextInt(10);

        location.setFloor(floor);
        location.setBuilding(building);
        location.setNodeType(nodeType);
        location.setLongName(longName);
        location.setShortName(shortName);
        location.setX(x);
        location.setY(y);

        return location;
    }
}
