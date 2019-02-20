package com.fms.main;

public class Facility {
    //facilityDetails and ID number per Facility
    private facilityDetails Details;
    private int facilityID;

    public Facility(){}

    //gettters and setters
    public facilityDetails getDetails(){
        return Details;
    }
    public void setDetails(facilityDetails Details){
        this.Details = Details;
    }
    public int getFacilityID(){
        return facilityID;
    }
    public void setFacilityID(int facilityID){
        this.facilityID = facilityID;
    }
}
