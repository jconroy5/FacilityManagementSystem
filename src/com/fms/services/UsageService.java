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
}
