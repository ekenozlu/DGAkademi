package com.ekenozlu.dgpays_w01.model;

public class Car {
    private String brandName;
    private String imageURL;
    private int doorNumber;
    private int engineNumber;
    private int wheelNumber;

    public Car() {
    }

    public Car(String brandName, String imageURL, int doorNumber, int engineNumber, int wheelNumber) {
        this.brandName = brandName;
        this.imageURL = imageURL;
        this.doorNumber = doorNumber;
        this.engineNumber = engineNumber;
        this.wheelNumber = wheelNumber;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(int doorNumber) {
        this.doorNumber = doorNumber;
    }

    public int getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(int engineNumber) {
        this.engineNumber = engineNumber;
    }

    public int getWheelNumber() {
        return wheelNumber;
    }

    public void setWheelNumber(int wheelNumber) {
        this.wheelNumber = wheelNumber;
    }

}
