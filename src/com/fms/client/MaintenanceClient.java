package com.fms.client;

import com.fms.main.Facility;
import com.fms.main.facilityDetails;
import com.fms.maintenance.Maintenance;
import com.fms.services.MaintenanceService;
import java.util.List;

public class MaintenanceClient {
    public MaintenanceClient() throws Exception {

        MaintenanceService maintenanceService = new MaintenanceService();

        /**
         * Below we'll be adding "office" locations for the fictitious "Super Company" as example Facilities.
         */

        //office1
        Facility office1 = new Facility();
        facilityDetails office1Details = new facilityDetails();
        office1.setFacilityID(1);
        office1Details.setName("Super Company New York");
        office1Details.setNumberOfRooms(10);
        office1.setDetails(office1Details);

        //office2
        Facility office2 = new Facility();
        facilityDetails office2Details = new facilityDetails();
        office2.setFacilityID(2);
        office2Details.setName("Super Company Atlanta");
        office2Details.setNumberOfRooms(17);
        office2.setDetails(office2Details);

        //office3
        Facility office3 = new Facility();
        facilityDetails office3Details = new facilityDetails();
        office3.setFacilityID(3);
        office3Details.setName("Super Company Los Angeles");
        office3Details.setNumberOfRooms(30);
        office3.setDetails(office3Details);

        //office4
        Facility office4 = new Facility();
        facilityDetails office4Details = new facilityDetails();
        office4.setFacilityID(4);
        office2Details.setName("Super Company Austin");
        office4Details.setNumberOfRooms(23);
        office4.setDetails(office4Details);

        //example maintenance request for office1
        System.out.println("\nMaintenanceClient: Creating New Maintenance Request...");
        Maintenance maintenance = maintenanceService.makeFacilityMaintRequest(office1, "WiFi APs are down", 50);
        System.out.println("\nMaintenanceClient: Maintenance Request has been created");

        //scheduling the example maintenance request
        System.out.println("\nMaintenanceClient: Scheduling Maintenance Request...");
        maintenanceService.scheduleMaintenance(maintenance);
        System.out.println("\nMaintenanceClient: Maintenance Request has been scheduled");

        //calculating total cost of maintenance request at office1
        System.out.println("\nMaintenanceClient: Calculating Total Maintenance Cost...");
        int totalCost = maintenanceService.calcMaintenanceCostForFacility(office1);
        System.out.println("The total cost of the maintenance at Facility #" + office1.getFacilityID() + " is $" + totalCost + ".");

        //listing CURRENT maintenance requests in a table, using example Facilities (offices, set up above)
        System.out.println("\nMaintenanceClient: Listing Current Maintenance Requests at Facility...");
        List<Maintenance> maintRequestList = maintenanceService.listMaintRequests(office2);
        Object[][] requests = new Object[maintRequestList.size() + 1][2];
        requests[0] = new Object[] {"Maintenance Request Details", "Cost"};
        for (int i = 1; i <= maintRequestList.size(); i++) {
            requests[i] = new Object[] {maintRequestList.get(i-1).getDetails(), maintRequestList.get(i-1).getCost()};
        }
        System.out.println("Current Maintenance Requests at Facility #" + office2.getFacilityID() + ":");
        for (Object[] row : requests) {
            System.out.format("   %-29s%6s\n", row);
        }

        //listing COMPLETED maintenance requests in a table, using example Facilities (offices, set up above)
        System.out.println("\nMaintenanceClient: Listing Completed Maintenance Requests at Facility...");
        List<Maintenance> maintenanceList = maintenanceService.listMaintenance(office2);
        Object[][] maintenanceTable = new Object[maintenanceList.size() + 1][2];
        maintenanceTable[0] = new Object[] {"Maintenance Details", "Cost"};
        for (int i = 1; i <= maintenanceList.size(); i++) {
            maintenanceTable[i] = new Object[] {maintenanceList.get(i-1).getDetails(), maintenanceList.get(i-1).getCost()};
        }
        System.out.println("Completed Maintenance Requests at Facility #" + office2.getFacilityID() + ":");
        for (Object[] row : maintenanceTable) {
            System.out.format("   %-30s%6s\n", row);
        }

        //listing Facility problems in a table
        System.out.println("\nMaintenanceClient: Listing Problems Affecting a Facility...");
        List<Maintenance> facilityProblemsList = maintenanceService.listFacilityProblems(office2);
        Object[][] problems = new Object[facilityProblemsList.size() + 1][2];
        problems[0] = new Object[] {"Problem Details", "Cost"};
        for (int i = 1; i <= facilityProblemsList.size(); i++) {
            problems[i] = new Object[] {facilityProblemsList.get(i-1).getDetails(), facilityProblemsList.get(i-1).getCost()};
        }
        System.out.println("Problems affecting Facility #" + office2.getFacilityID() + ":");
        for (Object[] row : problems) {
            System.out.format("   %-30s%6s\n", row);
        }

        //calculating the down-time for a Facility
        System.out.println("\nMaintenanceClient: Calculating Facility Down-Time...");
        int downTime = maintenanceService.calcDownTimeForFacility(office2);
        System.out.println("Facility #" + office2.getFacilityID() + " was down for maintenance for " + downTime + " days, "
                + "assuming each completed maintenance request took one work week (5 days) to complete.");

        //calculating the problem rate for a Facility
        System.out.println("\nMaintenanceClient: Calculating Facility Problem Rate...");
        double problemRate = maintenanceService.calcProblemRateForFacility(office2) * 100;
        System.out.print("\nThe problem rate at Facility #" + office2.getFacilityID() + " is ");
        System.out.format("%.2f", problemRate);
        System.out.print("%.");
    }
}
