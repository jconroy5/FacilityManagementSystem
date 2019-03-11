package com.fms.main;

public class Facility {

    //vars for FacilityDetails and ID number per Facility
    private FacilityDetails Details;
    private int facilityID;

    public Facility(){}

    //gettters and setters
    public FacilityDetails getDetails(){
        return Details;
    }
    public void setDetails(FacilityDetails Details){
        this.Details = Details;
    }
    public int getFacilityID(){
        return facilityID;
    }
    public void setFacilityID(int facilityID){
        this.facilityID = facilityID;
    }
}
