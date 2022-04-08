package edu.wpi.cs3733.D22.teamC.factory.floor;

import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;

import java.util.Random;

public class FloorFactory {
    private Random generator = new Random();

    public Floor create() {
        Floor floor = new Floor();

        int order = generator.nextInt(5);
        String longName = "Steve Harvey Temple";
        String shortName = "SH500";

        floor.setOrder(order);
        floor.setLongName(longName);
        floor.setShortName(shortName);

        return floor;
    }
}
