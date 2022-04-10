package edu.wpi.cs3733.D22.teamC.entity.location;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "LOCATION")
public class Location {
    @Id
    @Column(name = "ID")
    private String nodeID;
    
    @Column(name = "FloorID")
    private String floor; // TODO: Floors should be objects
    
    @Column(name = "Building")
    private String building = "";
    
    @Enumerated(EnumType.STRING)
    @Column(name = "NodeType")
    private NodeType nodeType;
    
    @Column(name = "LongName")
    private String longName = "";
    
    @Column(name = "ShortName")
    private String shortName = "";
    
    @Column(name = "XCoord")
    private int x = 0;
    
    @Column(name = "YCoord")
    private int y = 0;
    
    public enum NodeType {
        PATI,
        STOR,
        DIRT,
        HALL,
        ELEV,
        REST,
        STAI,
        DEPT,
        LABS,
        INFO,
        CONF,
        EXIT,
        RETL,
        SERV,
        BATH
    }
    
    public Location() {
        this.nodeID = UUID.randomUUID().toString();
    }
    
    public Location(
            String floor,
            String building,
            NodeType nodeType,
            String longName,
            String shortName,
            int x,
            int y) {
        this.nodeID = UUID.randomUUID().toString();
        this.floor = floor;
        this.building = building;
        this.nodeType = nodeType;
        this.longName = longName;
        this.shortName = shortName;
        this.x = x;
        this.y = y;
    }
    
    public String getNodeID() {
        return nodeID;
    }
    
    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }
    
    public String getFloor() {
        return floor;
    }
    
    public void setFloor(String floor) {
        this.floor = floor;
    }
    
    public String getBuilding() {
        return building;
    }
    
    public void setBuilding(String building) {
        this.building = building;
    }
    
    public NodeType getNodeType() {
        return nodeType;
    }
    
    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
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
    
    public int getX() {
        return x;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return nodeID.equals(location.nodeID)
                && floor.equals(location.floor)
                && x == location.x
                && y == location.y
                && building.equals(location.building)
                && nodeType == location.nodeType
                && longName.equals(location.longName)
                && shortName.equals(location.shortName);
    }
    
}
