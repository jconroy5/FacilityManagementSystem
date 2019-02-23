package com.fms.client;

import com.fms.main.*;
import com.fms.services.FacilityService;
import com.fms.services.UsageService;
import com.fms.usage.FacilityUse;
import com.fms.usage.FacilityInspection;
import java.time.LocalDate;
import java.util.List;

public class UsageClient {
    public UsageClient() throws Exception {

        UsageService useService = new UsageService();
        FacilityService facilityService = new FacilityService();

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

        System.out.println("\nUsageClient: Facility Inspections");

        //example inspection status for office4
        System.out.println("\n\tInspections At Facility #" + office4.getFacilityID());
        for (FacilityInspection inspection : useService.listInspections(office4)) {
            System.out.println("\t" + inspection.getInspection_type() +
                    " status: " + inspection.getInspection_detail());
        }

        //new Facility that will be checked for usage interval
        FacilityUse factUse = new FacilityUse();;
        factUse.setFacilityID(5);
        facilityDetails factDet = new facilityDetails();
        factDet.setNumberOfRooms(20);
        factDet.setName("Super Company Chicago");
        factDet.setFacilityID(5);
        factUse.setDetails(factDet);
        facilityService.addNewFacility(factUse);
        factUse.setStartDate(LocalDate.of(2019, 1, 14));
        factUse.setEndDate(LocalDate.of(2019, 5, 3));
        factUse.setRoomNumber(1);

        System.out.println("\nUsageClient: Checking if Facility is in use...");

        boolean result = useService.isInUseDuringInterval(factUse);

        System.out.print("\tFacility #" + factUse.getFacilityID());
        if (factUse.getRoomNumber() != 0) {
            System.out.print(" - Room " + factUse.getRoomNumber());
        }
        //if in use,
        if (result) {
            System.out.print(" IS ");
            //if not in use,
        } else {
            System.out.print(" is NOT ");
        }
        System.out.print("in use from " + factUse.getStartDate() + " to " + factUse.getEndDate() + ".\n");

        //assign the facility to use during the previously checked room and start/end date
        System.out.println("\nUsageClient: Assigning Facility Usage...");
        useService.assignFacilityToUse(factUse);
        System.out.println("\nUsageClient: Facility/Room has been assigned");

        //checking again if facility is in use
        System.out.println("\nUsageClient: Checking if Facility is in use...");

        boolean result2 = useService.isInUseDuringInterval(factUse);

        System.out.print("\tFacility #" + factUse.getFacilityID());
        if (factUse.getRoomNumber() != 0) {
            System.out.print(" - Room " + factUse.getRoomNumber());
        }
        if (result2) {
            System.out.print(" IS ");
        } else {
            System.out.print(" is NOT ");
        }
        System.out.print("in use from " + factUse.getStartDate() + " to " + factUse.getEndDate() + ".\n");

        //Generate list of actual usage of given Facility
        System.out.println("\nUsageClient: Calculating actual usage of Facility...");

        List<FacilityUse> usageList = useService.listActualUsage(factUse);
        Object[][] usage = new Object[usageList.size() + 1][3];
        usage[0] = new Object[] {"Room #", "Start Date", "End Date"};
        for (int i = 1; i <= usageList.size(); i++) {
            usage[i] = new Object[] {usageList.get(i-1).getRoomNumber(), usageList.get(i-1).getStartDate().toString(),
                    usageList.get(i-1).getEndDate().toString()};
            if ((int) usage[i][0] == 0) {
                usage[i][0] = "all";
            }
        }
        System.out.println("Usage at Facility #" + factUse.getFacilityID());
        for (Object[] row : usage) {
            System.out.format("\t%-10s%-15s%-15s\n", row);
        }

        System.out.println("\nUsageClient: Vacating Facility...");
        useService.vacateFacility(factUse, 1);
        System.out.println("\nUsageClient: Facility has been vacated");

        //check Facility usage after being vacated
        System.out.println("\nUsageClient: Calculating Facility usage after being vacated...");

        List<FacilityUse> usageList2 = useService.listActualUsage(factUse);
        Object[][] usage2 = new Object[usageList2.size() + 1][3];
        usage2[0] = new Object[] {"Room #", "Start Date", "End Date"};
        for (int i = 1; i <= usageList2.size(); i++) {
            usage2[i] = new Object[] {usageList2.get(i-1).getRoomNumber(), usageList2.get(i-1).getStartDate().toString(),
                    usageList2.get(i-1).getEndDate().toString()};
            if ((int) usage2[i][0] == 0) {
                usage2[i][0] = "all";
            }
        }
        System.out.println("Usage at Facility #" + factUse.getFacilityID());
        for (Object[] row : usage2) {
            System.out.format("\t%-10s%-15s%-15s\n", row);
        }

        //calculate the current usage rate of a facility
        System.out.println("\nUsageClient: Calculating the current usage rate of Facility...");
        int usageRate = (int) (useService.calcUsageRate(office2) * 100);
        System.out.println("Current usage rate at Facility #" + office2.getFacilityID() + " is " + usageRate + "%.");

        //removes office5 so it can be tested again later
        facilityService.removeFacility(5);
    }
}
