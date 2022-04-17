package edu.wpi.cs3733.D22.teamC.entity.floor;

import edu.wpi.cs3733.D22.teamC.entity.generic.IDEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "FLOOR")
public class Floor implements IDEntity {
    @Id
    @Column(name = "ID")
    private String ID;
    
    @Column(name = "FloorOrder")
    private int order;
    
    @Column(name = "LongName")
    private String longName;
    
    @Column(name = "ShortName")
    private String shortName;

    @Column(name = "Description")
    private String description;

    @Column(name ="ImageSrc")
    private String imageSrc;

    @Lob
    @Column(name = "Image")
    private byte[] image;

    public Floor(){
        this.ID = UUID.randomUUID().toString();
    }

    public Floor(int order, String longName, String shortName) {
        this.ID = UUID.randomUUID().toString();
        this.order = order;
        this.longName = longName;
        this.shortName = shortName;
    }
    public Floor(String ID){
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String floorID) {
        this.ID = floorID;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Floor floor = (Floor) o;
        return ID.equals(floor.ID)
                && order == floor.order
                && longName.equals(floor.longName)
                && shortName.equals(floor.shortName)
                && description.equals(floor.description);
    }

    @Override
    public String toString() {
        return getShortName();
    }
}
