package com.fms.maintenance;
import com.fms.main.Facility;

public interface Maintenance {

    //declaring getters and setters
    public Facility getFacility();
    public void setFacility(Facility facility);
    public int getFacilityID();
    public void setFacilityID(int facilityID);
    public int getCost();
    public void setCost(int cost);
    public String getMaintenanceDetails();
    public void setMaintenanceDetails(String maintenanceDetails);
    public int getMaintenanceID();
    public void setMaintenanceID(int maintenanceID);
    public int getMaintenanceRequestID();
    public void setMaintenanceRequestID(int maintenanceRequestID);
}
