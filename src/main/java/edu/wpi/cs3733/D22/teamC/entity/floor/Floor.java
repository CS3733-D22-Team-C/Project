package edu.wpi.cs3733.D22.teamC.entity.floor;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "FLOOR")
public class Floor {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int floorID;
    
    @Column(name = "FloorOrder")
    private int order;
    
    @Column(name = "LongName")
    private String longName;
    
    @Column(name = "ShortName")
    private String shortName;

    public Floor(){}

    public Floor(int order, String longName, String shortName) {
        this.order = order;
        this.longName = longName;
        this.shortName = shortName;
    }
    public Floor(int floorID){
        this.floorID = floorID;
    }

    public int getFloorID() {
        return floorID;
    }

    public void setFloorID(int floorID) {
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


}

