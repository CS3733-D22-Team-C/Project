package edu.wpi.cs3733.D22.teamC.entity.location;

public class Location {
    private int nodeID;
    private int floor; // TODO: Floors should be objects
    private String building = "";
    private NodeType nodeType;
    private String longName = "";
    private String shortName = "";
    private int x = 0, y = 0;

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

    public Location() {}

    public Location(int nodeID) {
        this.nodeID = nodeID;
    }

    public Location(int floor, String building, NodeType nodeType, String longName, String shortName, int x, int y) {
        this.floor = floor;
        this.building = building;
        this.nodeType = nodeType;
        this.longName = longName;
        this.shortName = shortName;
        this.x = x;
        this.y = y;
    }


    @Deprecated
    public Location(int nodeID, int floor, String building, NodeType nodeType, String longName, String shortName, int x, int y) {
        this.nodeID = nodeID;
        this.floor = floor;
        this.building = building;
        this.nodeType = nodeType;
        this.longName = longName;
        this.shortName = shortName;
        this.x = x;
        this.y = y;
    }

    public int getNodeID() {
        return nodeID;
    }

    public void setNodeID(int nodeID) {
        this.nodeID = nodeID;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
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
}
