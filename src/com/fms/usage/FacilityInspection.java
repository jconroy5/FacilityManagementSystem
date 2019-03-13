package com.fms.usage;

import com.fms.main.Facility;

public interface FacilityInspection {

    //declaring getters and setters
    public Facility getFacility();
    public void setFacility(Facility facility);
    public int getFacilityID();
    public void setFacilityID(int facility_ID);
    public String getInspection_type();
    public void setInspection_type(String inspection_type);
    public String getInspection_detail();
    public void setInspection_detail(String inspection_detail);
    public int getInspectionID();
    public void setInspectionID(int inspectionID);
}