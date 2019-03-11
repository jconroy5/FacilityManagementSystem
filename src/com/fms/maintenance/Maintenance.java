package com.fms.maintenance;

public class Maintenance {

    //vars for maintenance cost and optional details
    private int cost;
    private String maintenanceDetails;

    public Maintenance(){}

    //getters and setters
    public int getCost(){
        return cost;
    }
    public void setCost(int cost){
        this.cost = cost;
    }
    public String getMaintenanceDetails(){
        return maintenanceDetails;
    }
    public void setMaintenanceDetails(String maintenanceDetails){
        this.maintenanceDetails = maintenanceDetails;
    }
}
