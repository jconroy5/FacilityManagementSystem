package com.fms.DAL;

import com.fms.main.Facility;
import com.fms.maintenance.Maintenance;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Modeled after CustomerDAO.java from BookStore, COMP 373/473, Spring 2019.
 */
public class MaintenanceDAO {

    public MaintenanceDAO() {}

    /**
     * Creates a new Maintenance request
     * @param facility the Facility where the maintenance will be done
     * @param maintenanceDetails information about the maintenance request
     * @param cost the cost of the maintenance at the given Facility
     * @return
     */
    public Maintenance makeFacilityMaintRequest(Facility facility, String maintenanceDetails, int cost) {

        try {

            Maintenance maint = new Maintenance();
            maint.setMaintenanceDetails(maintenanceDetails);
            maint.setCost(cost);
            maint.setFacilityID(facility.getFacilityID());

            Statement st = DBHelper.getConnection().createStatement();
            String makeMaintRequestQuery = "INSERT INTO maint_request (facility_id, details, cost) VALUES (" +
                    facility.getFacilityID() + ", '" + maintenanceDetails + "', " + cost + ")";
            st.execute(makeMaintRequestQuery);
            System.out.println("MaintenanceDAO: ********** Query " + makeMaintRequestQuery + "\n");

            //close to manage resources
            st.close();

            return maint;
        }
        catch (SQLException se) {
            System.err.println("MaintenanceDAO: Threw a SQLException making a maintenance request.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }
        return null;
    }


    /**
     * Schedules a new Maintenance Request
     * Requests are first added, then removed from the maint_request table
     * @param maintenanceRequest the maintenance that is requested at a given Facility
     */
    public void scheduleMaintenance(Maintenance maintenanceRequest) {

        try {
            Statement st = DBHelper.getConnection().createStatement();
            String scheduleMaintenanceAddQuery = "INSERT INTO maintenance (facility_id, details, cost) VALUES (" +
                    maintenanceRequest.getFacilityID() + ", '" + maintenanceRequest.getDetails() +
                    "', " + maintenanceRequest.getCost() + ")";
            st.execute(scheduleMaintenanceAddQuery);
            System.out.println("MaintenanceDAO: ********** Query " + scheduleMaintenanceAddQuery + "\n");

            //close to manage resources
            st.close();
        }
        catch (SQLException se) {
            System.err.println("MaintenanceDAO: Threw a SQLException adding a maintenance "
                    + "request to maintenance table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }
        try {

            Statement st = DBHelper.getConnection().createStatement();
            String scheduleMaintenanceRemoveQuery = "DELETE FROM maint_request WHERE facility_id = " +
                    maintenanceRequest.getFacilityID() + " AND details = '" + maintenanceRequest.getDetails() +
                    "' AND cost = " + maintenanceRequest.getCost();
            st.execute(scheduleMaintenanceRemoveQuery);
            System.out.println("MaintenanceDAO: ********** Query " + scheduleMaintenanceRemoveQuery + "\n");

            //close to manage resources
            st.close();
        }
        catch (SQLException se) {
            System.err.println("MaintenanceDAO: Threw a SQLException removing a "
                    + "maintenance request from maint_request table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }
    }
}
