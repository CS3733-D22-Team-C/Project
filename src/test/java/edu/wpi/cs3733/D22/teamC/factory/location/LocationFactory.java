package edu.wpi.cs3733.D22.teamC.factory.location;

import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.factory.Factory;

import java.util.Random;

public class LocationFactory implements Factory<Location> {
    private Random generator = new Random();

    public Location create() {
        Location location = new Location();

        Floor testFloor = new Floor();
        FloorDAO floorDAO = new FloorDAO();
        floorDAO.insert(testFloor);

        String building = "Wong Labs";
        Location.NodeType nodeType = Location.NodeType.values()[generator.nextInt(Location.NodeType.values().length)];
        String longName = "Cunning Chimera";
        String shortName = "CC1000";
        int x = generator.nextInt(10), y = generator.nextInt(10);

        location.setFloor(testFloor);
        location.setBuilding(building);
        location.setNodeType(nodeType);
        location.setLongName(longName);
        location.setShortName(shortName);
        location.setX(x);
        location.setY(y);

        return location;
    }
    
    public static String generateRandomString(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%&";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }
}
