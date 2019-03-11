package com.fms.main;

public class FacilityDetails {
    //vars for facility name, location, phone number, and the number of rooms on site
    private String name;
    private long phoneNumber;
    private int numberOfRooms;

    public FacilityDetails(){}

    //getters and setters
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public long getPhoneNumber(){
        return phoneNumber;
    }
    public void setPhoneNumber(long phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public int getNumberOfRooms() {
        return numberOfRooms;
    }
    public void setNumberOfRooms(int numberOfRooms){
        this.numberOfRooms = numberOfRooms;
    }
}
