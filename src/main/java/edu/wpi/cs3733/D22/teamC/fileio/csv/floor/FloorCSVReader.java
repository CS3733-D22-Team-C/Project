package edu.wpi.cs3733.D22.teamC.fileio.csv.floor;

import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.fileio.csv.CSVReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FloorCSVReader extends CSVReader<Floor> {

    /**
     * Maps Floor (header, value) pairs to a value to change for the object.
     * @param object The object to be modified.
     * @param header The header to be mapped to an attribute.
     * @param value The value for the current attribute.
     * @return The Floor modified with the value at the corresponding attribute.
     */
    @Override
    protected Floor parseAttribute(Floor object, String header, String value) {
        switch (header) {
            case "id":
                object.setID(value);
                break;
            case "order":
                object.setOrder(Integer.parseInt(value));
                break;
            case "longName":
                object.setLongName(value);
                break;
            case "shortName":
                object.setShortName(value);
                break;
            case "description":
                object.setDescription(value);
                break;
            case "imageSrc":
                object.setImageSrc(value);

                try {
                    Path filePath = Paths.get("maps/" + object.getImageSrc());
                    //Image image = new Image("file:" + filePath);
                    File imgPath = new File(filePath.toString());
                    BufferedImage bImg = ImageIO.read(imgPath);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageIO.write(bImg, "png", byteArrayOutputStream);
                    object.setImage(byteArrayOutputStream.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;
        }
        return object;
    }

    /**
     * Wrapper for Floor constructor.
     * @return Newly created Floor Object.
     */
    @Override
    protected Floor createObject() {
        return new Floor();
    }
}
