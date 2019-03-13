package com.fms.services;

import com.fms.DAL.UsageDAO;
import com.fms.usage.*;
import com.fms.main.*;
import java.util.List;
import java.time.*;

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
    public boolean isInUseDuringInterval(Facility facility, FacilityUse facilityUse) {
        //checks for a valid start date
        if (facilityUse.getStartDate().isAfter(facilityUse.getEndDate())) {
            System.out.println("The start date cannot be after the end date.");
            //checks for a valid number of rooms
        } else if (facilityUse.getRoomNumber() > facility.getFacilityDetail().getNumberOfRooms()) {
            System.out.println("Invalid room number. There are only " +
                    facility.getFacilityDetail().getNumberOfRooms() +
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
    public void assignFacilityToUse(Facility facility, FacilityUse facilityUse) {
        //check if the start date is valid
        if (facilityUse.getStartDate().isAfter(facilityUse.getEndDate())) {
            System.out.println("The start date cannot be after the end date.");
            //check if the room number is valid
        } else if (facilityUse.getRoomNumber() > facility.getFacilityDetail().getNumberOfRooms()) {
            System.out.println("Invalid room number. There are only " +
                    facility.getFacilityDetail().getNumberOfRooms() +
                    " rooms at this facility.");
            //check if given room is already in use during interval
        } else if (isInUseDuringInterval(facility, facilityUse)) {
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

    //Returns the date on which a given Facility was started
    public LocalDate getFacilityStartDate(Facility facility) {
        try {
            return usageDAO.getFacilityStartDate(facility);
        } catch (Exception se) {
            System.err.println("UseService: Threw an Exception retrieving the facility start date.");
            System.err.println(se.getMessage());
        }
        return null;
    }

    //vacates a room at a given Facility, if it is currently in use
    public void vacateFacility(Facility facility, int roomNumber) {
        try {
            List<FacilityUse> usageList = listActualUsage(facility);
            //check if room number is valid
            if (roomNumber > facility.getFacilityDetail().getNumberOfRooms()) {
                System.out.println("Invalid room number. There are only " +
                        facility.getFacilityDetail().getNumberOfRooms() + " rooms at this facility.");
            } else {
                for (FacilityUse use : usageList) {
                    //check if room is in use
                    if (use.getRoomNumber() == 0 || (use.getRoomNumber() == roomNumber))  {
                        if ((LocalDate.now().equals(use.getStartDate())) || LocalDate.now().isAfter(use.getStartDate())) {
                            //if room is in use, vacate, else print vacate denial message
                            if ((LocalDate.now().equals(use.getEndDate())) || (LocalDate.now().isBefore(use.getEndDate()))) {
                                usageDAO.vacateFacility(facility, roomNumber);
                            }
                        } else {
                            System.out.println("This room is not in use. Vacate request denied.");
                        }
                    }
                }
            }
        }
        catch (Exception se) {
            System.err.println("UseService: Threw an Exception vacating a facility.");
            System.err.println(se.getMessage());
        }
    }

    //calculate usage of a given Facility
    public double calcUsageRate(Facility facility) {
        try {
            FacilityService facilityService = new FacilityService();
            int totalRooms = facility.getFacilityDetail().getNumberOfRooms();
            int roomsAvailable = facilityService.requestAvailableCapacity(facility);
            int roomsInUse = totalRooms - roomsAvailable;
            return Math.round(((double)roomsInUse / totalRooms) * 100d)/100d;
        } catch (Exception se) {
            System.err.println("UseService: Threw an Exception retrieving list of usage for calculating the usage rate.");
            System.err.println(se.getMessage());
        }
        return 0.00;
    }
}
