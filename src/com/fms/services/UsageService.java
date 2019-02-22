package com.fms.services;

import com.fms.DAL.UsageDAO;
import com.fms.usage.*;
import com.fms.main.*;
import java.util.List;

public class UsageService {

    private UsageDAO usageDAO = new UsageDAO();

    //generates a list of inspections at a given Facility
    public List<FacilityInspection> listInspections(Facility facility) {
        try {
            return usageDAO.listInspections(facility);
        } catch (Exception se) {
            System.err.println("UseService: Threw an Exception retrieving list of inspections.");
            System.err.println(se.getMessage());
        }
        return null;
    }

    //checks if a Facility is in use during a given interval
    public boolean isInUseDuringInterval(FacilityUse facilityUse) {
        //checks for a valid start date
        if (facilityUse.getStartDate().isAfter(facilityUse.getEndDate())) {
            System.out.println("The start date cannot be after the end date.");
            //checks for a valid number of rooms
        } else if (facilityUse.getRoomNumber() > facilityUse.getDetails().getNumberOfRooms()) {
            System.out.println("Invalid room number. There are only " +
                    facilityUse.getDetails().getNumberOfRooms() +
                    " rooms at this facility.");
        } else {
            try {
                return usageDAO.isInUseDuringInterval(facilityUse);
            } catch (Exception se) {
                System.err.println("UseService: Threw an Exception checking if facility is in use during interval.");
                System.err.println(se.getMessage());
            }
        }
        return true;
    }

    //generates list of usage at a given Facility
    public List<FacilityUse> listActualUsage(Facility facility) {
        try {
            return usageDAO.listActualUsage(facility);
        } catch (Exception se) {
            System.err.println("UseService: Threw an Exception retrieving list of usage.");
            System.err.println(se.getMessage());
        }
        return null;
    }

    //assigns a room number at a given Facility to use
    public void assignFacilityToUse(FacilityUse facilityUse) {
        //check if the start date is valid
        if (facilityUse.getStartDate().isAfter(facilityUse.getEndDate())) {
            System.out.println("The start date cannot be after the end date.");
            //check if the room number is valid
        } else if (facilityUse.getRoomNumber() > facilityUse.getDetails().getNumberOfRooms()) {
            System.out.println("Invalid room number. There are only " +
                    facilityUse.getDetails().getNumberOfRooms() +
                    " rooms at this facility.");
            //check if given room is already in use during interval
        } else if (isInUseDuringInterval(facilityUse)) {
            System.out.println("This room is already in use during this interval.");
        } else {
            try {
                usageDAO.assignFacilityToUse(facilityUse);
            } catch (Exception se) {
                System.err.println("UseService: Threw an Exception assigning a facility to use.");
                System.err.println(se.getMessage());
            }
        }
    }
}
