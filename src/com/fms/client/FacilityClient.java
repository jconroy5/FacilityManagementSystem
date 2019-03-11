package com.fms.client;

import com.fms.main.*;
import com.fms.services.*;
import java.util.List;

public class FacilityClient {
    public FacilityClient() throws Exception {

        FacilityService facilityService = new FacilityService();

        /**
         * Below we'll be adding "office" locations for the fictitious "Super Company" as example Facilities.
         */

        //office1
        Facility office1 = new Facility();
        FacilityDetails office1Details = new FacilityDetails();
        office1.setFacilityID(1);
        office1Details.setName("Super Company New York");
        office1Details.setLocation("New York, NY");
        office1Details.setNumberOfRooms(10);
        office1.setDetails(office1Details);

        //office2
        Facility office2 = new Facility();
        FacilityDetails office2Details = new FacilityDetails();
        office2.setFacilityID(2);
        office2Details.setName("Super Company Atlanta");
        office2Details.setLocation("Atlanta, GA");
        office2Details.setNumberOfRooms(17);
        office2.setDetails(office2Details);

        //office3
        Facility office3 = new Facility();
        FacilityDetails office3Details = new FacilityDetails();
        office3.setFacilityID(3);
        office3Details.setName("Super Company Los Angeles");
        office3Details.setLocation("Los Angeles, CA");
        office3Details.setNumberOfRooms(30);
        office3.setDetails(office3Details);

        //office4
        Facility office4 = new Facility();
        FacilityDetails office4Details = new FacilityDetails();
        office4.setFacilityID(4);
        office4Details.setName("Super Company Austin");
        office4Details.setLocation("Austin, TX");
        office4Details.setNumberOfRooms(23);
        office4.setDetails(office4Details);

        //creating office5
        System.out.println("\nFacilityClient: Now creating a new Facility");
        Facility office5 = new Facility();
        office5.setFacilityID(5);
        FacilityDetails detail = new FacilityDetails();
        detail.setName("Super Company Chicago");
        detail.setLocation("Chicago, IL");
        detail.setNumberOfRooms(20);
        office5.setDetails(detail);

        facilityService.addNewFacility(office5);
        System.out.println("FacilityClient: Facility is being added to Facility Database...");
        System.out.println("FacilityClient: Searching for info about this Facility...");
        Facility searchedFacility = facilityService.getFacilityInformation(5);

        System.out.println("\nFacilityClient: Facility Information");
        System.out.println("\n\tFacility ID:   \t\t" + searchedFacility.getFacilityID());
        FacilityDetails facilityDet = searchedFacility.getDetails();
        System.out.println("\tInfo About Facility:  \t" + facilityDet.getName() +
                "\n\t\t\t\t Number of Rooms:" + facilityDet.getNumberOfRooms());
        if (facilityDet.getPhoneNumber() != 0) {
            System.out.print("\t\t\t\t Phone Number: " + facilityDet.getPhoneNumber() +
                    "\n\t\t\t\t" + "\n");
        } else {
            System.out.print("\t\t\t\t Phone Number: unlisted" +
                    "\n\t\t\t\t" + "\n");
        }

        //adding a phone number to office5's FacilityDetails
        facilityService.addFacilityDetail(5, 312555999);
        Facility updatedFacility = facilityService.getFacilityInformation(5);
        FacilityDetails facilityNewDet = updatedFacility.getDetails();

        System.out.println("\nFacilityClient: Facility Information has been updated!");
        System.out.println("\n\tFacility ID:   \t\t" + updatedFacility.getFacilityID());
        System.out.println("\tInfo About Facility:  \t" + facilityNewDet.getName() +
                "\n\t\t\t\t Number of Rooms: " + facilityNewDet.getNumberOfRooms());
        if (facilityNewDet.getPhoneNumber() != 0) {
            System.out.print("\t\t\t\t Phone Number: " + facilityNewDet.getPhoneNumber() +
                    "\n\t\t\t\t" + "\n");
        } else {
            System.out.print("\t\t\t\t Phone Number: unlisted" +
                    "\n\t\t\t\t" + "\n");
        }

        System.out.println("\nFacilityClient: Remove a Facility");
        facilityService.removeFacility(5);
        System.out.println("Facility has been removed");

        //print updated list of Facilities after removing office5
        System.out.println("\nFacilityClient: List of current Facilities");
        List<Facility> listOfFacilities = facilityService.listFacilities();
        for (Facility fac : listOfFacilities) {
            FacilityDetails facDet = fac.getDetails();
            System.out.println("\n\t" + facDet.getName() + " ID: " + fac.getFacilityID());
        }

        //requesting the available capacity of office4
        System.out.println("\nFacilityClient: Requesting Facility's Available Capacity...");
        int roomsAvail = facilityService.requestAvailableCapacity(office4);
        System.out.println("There are " + roomsAvail + " rooms currently available at Facility #" + office4.getFacilityID() + ".");
    }
}