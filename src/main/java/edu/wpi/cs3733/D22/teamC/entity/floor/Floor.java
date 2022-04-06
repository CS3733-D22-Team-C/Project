package edu.wpi.cs3733.D22.teamC.entity.floor;

public class Floor {

    private int floorID;
    private int order;
    private String longName;
    private String shortName;
    private String imageSrc;

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

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }
}
