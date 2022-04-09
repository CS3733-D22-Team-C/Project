package edu.wpi.cs3733.D22.teamC.entity.floor;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "FLOOR")
public class Floor {
    @Id
    @Column(name = "ID")
    private String floorID;
    
    @Column(name = "FloorOrder")
    private int order;
    
    @Column(name = "LongName")
    private String longName;
    
    @Column(name = "ShortName")
    private String shortName;
    
    @Column(name = "ImageSrc")
    private String imageSrc;

    public Floor(){
        this.floorID = UUID.randomUUID().toString();
    }

    public Floor(int order, String longName, String shortName) {
        this.floorID = UUID.randomUUID().toString();
        this.order = order;
        this.longName = longName;
        this.shortName = shortName;
    }
    public Floor(String floorID){
        this.floorID = floorID;
    }

    public String getFloorID() {
        return floorID;
    }

    public void setFloorID(String floorID) {
        this.floorID = floorID;
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

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Floor floor = (Floor) o;
        return floorID == floor.floorID
                && order == floor.order
                && longName.equals(floor.longName)
                && shortName.equals(floor.shortName)
                && imageSrc.equals(floor.imageSrc);
    }

}
