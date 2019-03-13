package com.fms.main;

public interface FacilityDetails {

    //declaring getters and setters
    public Facility getFacility();
    public String getName();
    public void setName(String name);
    public int getFacilityID();
    public void setFacilityID(int facilityID);
    public String getLocation();
    public void setLocation(String location);
    public long getPhoneNumber();
    public void setPhoneNumber(long phoneNumber);
    public int getNumberOfRooms();
    public void setNumberOfRooms(int numberOfRooms);
}
