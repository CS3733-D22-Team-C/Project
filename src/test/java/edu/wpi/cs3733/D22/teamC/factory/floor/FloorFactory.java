package edu.wpi.cs3733.D22.teamC.factory.floor;

import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.factory.Factory;

import java.awt.image.FilteredImageSource;
import java.util.Random;

public class FloorFactory implements Factory<Floor> {
    private Random generator = new Random();

    public Floor create() {
        Floor floor = new Floor();

        int order = generator.nextInt(5);
        String longName = "Steve Harvey Temple";
        String shortName = "SH500";
        //byte[] img = null;

        floor.setOrder(order);
        floor.setLongName(longName);
        floor.setShortName(shortName);
        //floor.setImageSrc(img);

        return floor;
    }
}
