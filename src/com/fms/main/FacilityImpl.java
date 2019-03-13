package com.fms.main;

public class FacilityImpl implements Facility{
    private int facilityID;
    private FacilityDetails facilityDetail;

    public FacilityImpl() {}

    //getters and setters
    public FacilityDetails getFacilityDetail() {
        return facilityDetail;
    }
    public void setFacilityDetail(FacilityDetails facilityDetail) {
        this.facilityDetail = facilityDetail;
    }
    public void setFacilityID(int facilityID) {
        this.facilityID = facilityID;
    }
    public int getFacilityID() {
        return facilityID;
    }
}
