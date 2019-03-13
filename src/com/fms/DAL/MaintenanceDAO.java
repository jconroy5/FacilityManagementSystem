package com.fms.DAL;

import com.fms.main.Facility;
import com.fms.maintenance.*;
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
     * @return the Requested Maintenance object
     */
    public Maintenance makeFacilityMaintRequest(Facility facility, String maintenanceDetails, int cost) {

        try {

            Maintenance maint = new MaintenanceImpl();
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
                    maintenanceRequest.getFacilityID() + ", '" + maintenanceRequest.getMaintenanceDetails() +
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
                    maintenanceRequest.getFacilityID() + " AND details = '" + maintenanceRequest.getMaintenanceDetails() +
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


    /**
     * Calculates sum of maintenance costs at a given Facility
     * @param facility the Facility where the maintenance took place
     * @return The sum of maintenance costs
     */
    public int calcMaintenanceCostForFacility(Facility facility) {

        try {

            int totalCost = 0;

            Statement st = DBHelper.getConnection().createStatement();
            String calcMaintenanceCostQuery = "SELECT SUM(cost) FROM maintenance "
                    + "WHERE facility_id = " + facility.getFacilityID();
            ResultSet maintRS = st.executeQuery(calcMaintenanceCostQuery);

            while ( maintRS.next() ) {
                totalCost = maintRS.getInt(1);
            }
            System.out.println("MaintenanceDAO: ********** Query " + calcMaintenanceCostQuery + "\n");

            //close to manage resources
            maintRS.close();
            st.close();
            return totalCost;
        }
        catch (SQLException se) {
            System.err.println("MaintenanceDAO: Threw a SQLException calculating total "
                    + "maintenance cost from maintenance table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }
        return 0;
    }

    /**
     * Generates list of maintenance requests from maint_table
     * @param facility the Facility whose maintenance requests are being listed
     * @return List of current maintenance requests for a given Facility
     */
    public List<Maintenance> listMaintRequests(Facility facility) {

        List<Maintenance> listOfMaintRequests = new ArrayList<Maintenance>();

        try {

            Statement st = DBHelper.getConnection().createStatement();
            String listMaintRequestsQuery = "SELECT * FROM maint_request WHERE facility_id = '" +
                    facility.getFacilityID() + "' ORDER BY cost";

            ResultSet maintRS = st.executeQuery(listMaintRequestsQuery);
            System.out.println("UseDAO: ********** Query " + listMaintRequestsQuery + "\n");

            while ( maintRS.next() ) {
                Maintenance maintenanceRequest = new MaintenanceImpl();
                maintenanceRequest.setMaintenanceDetails(maintRS.getString("maintenanceDetails"));
                maintenanceRequest.setCost(maintRS.getInt("cost"));
                maintenanceRequest.setFacilityID(facility.getFacilityID());
                listOfMaintRequests.add(maintenanceRequest);
            }

            //close to manage resources
            maintRS.close();
            st.close();
        }
        catch (SQLException se) {
            System.err.println("UseDAO: Threw a SQLException retreiving list of maintenance "
                    + "requests from maint_request table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }
        return listOfMaintRequests;
    }


    /**
     * Generates list of maintenance requests that have been completed at a given Facility
     * @param facility the Facility whose completed maintenance requests are being listed
     * @return List of completed maintenance requests for a given Facility
     */
    public List<Maintenance> listMaintenance(Facility facility) {

        List<Maintenance> listOfCompletedMaintenance = new ArrayList<Maintenance>();

        try {

            Statement st = DBHelper.getConnection().createStatement();
            String listMaintenanceQuery = "SELECT * FROM maintenance WHERE facility_id = '" +
                    facility.getFacilityID() + "' ORDER BY cost";

            ResultSet maintRS = st.executeQuery(listMaintenanceQuery);
            System.out.println("UseDAO: ********** Query " + listMaintenanceQuery + "\n");

            while ( maintRS.next() ) {
                Maintenance maintenance = new MaintenanceImpl();
                maintenance.setMaintenanceDetails(maintRS.getString("maintenanceDetails"));
                maintenance.setCost(maintRS.getInt("cost"));
                maintenance.setFacilityID(facility.getFacilityID());
                listOfCompletedMaintenance.add(maintenance);
            }

            //close to manage resources
            maintRS.close();
            st.close();
        }
        catch (SQLException se) {
            System.err.println("UseDAO: Threw a SQLException retrieving list of maintenance "
                    + "from maint_table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }
        return listOfCompletedMaintenance;
    }
}
