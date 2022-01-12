package com.example.senku;

public class CellModel {

    private String bgRotation;
    private int bgId;
    private int circleId;

    public CellModel(String bgRotation, int bgId, int circleId) {
        this.bgRotation = bgRotation;
        this.bgId = bgId;
        this.circleId = circleId;
    }

    public String getBgRotation() {
        return bgRotation;
    }

    public void setBgRotation(String bgRotation) {
        this.bgRotation = bgRotation;
    }

    public int getBgId() {
        return bgId;
    }

    public void setBgId(int bgId) {
        this.bgId = bgId;
    }

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

}
