package com.fms.services;

import com.fms.DAL.FacilityDAO;
import com.fms.DAL.UsageDAO;
import com.fms.usage.FacilityUse;
import com.fms.main.Facility;
import java.time.LocalDate;
import java.util.List;

public class FacilityService {

    private FacilityDAO facilityDAO = new FacilityDAO();
    private UsageDAO usageDAO = new UsageDAO();

    //Add new Facility to DB
    public void addNewFacility(Facility facility) {
        try {
            facilityDAO.addNewFacility(facility);
        } catch (Exception se) {
            System.err.println("FacilityService: Threw an Exception adding new facility.");
            System.err.println(se.getMessage());
        }
    }

    //gets Facility's info
    public Facility getFacilityInformation(int id) {

        try {
            Facility facility = facilityDAO.getFacilityInformation(id);
            return facility;
        } catch (Exception se) {
            System.err.println("FacilityService: Threw an Exception retrieving facility.");
            System.err.println(se.getMessage());
        }
        return null;
    }

    //removes Facility listing from DB by ID
    public void removeFacility(int id) {

        try {
            facilityDAO.removeFacility(id);
        } catch (Exception se) {
            System.err.println("FacilityService: Threw an Exception removing facility.");
            System.err.println(se.getMessage());
        }
    }

    //adds phone number to a Facility listing in DB by Facility ID
    public void addFacilityDetail(int ID, int phoneNumber) {
        try {
            facilityDAO.addFacilityDetail(ID, phoneNumber);
        } catch (Exception se) {
            System.err.println("FacilityService: Threw an Exception updating phone in facility_detail.");
            System.err.println(se.getMessage());
        }
    }

    //lists available Facilities
    public List<Facility> listFacilities() {
        try {
            return facilityDAO.listFacilities();
        } catch (Exception se) {
            System.err.println("FacilityService: Threw an Exception retrieving list of facilities.");
            System.err.println(se.getMessage());
        }
        return null;
    }

    //generates list of available Facilities by rooms in use
    public int requestAvailableCapacity(Facility facility) {

        try {
            List<FacilityUse> usage = usageDAO.listActualUsage(facility);
            int roomsInUse = 0;
            if (usage.size() > 0) {
                for (FacilityUse facUse : usage) {
                    //if Facility is currently in use,
                    if ((LocalDate.now().equals(facUse.getStartDate()) || LocalDate.now().isAfter(facUse.getStartDate())) &
                            LocalDate.now().equals(facUse.getEndDate()) || LocalDate.now().isBefore(facUse.getEndDate())) {
                        if (facUse.getRoomNumber() == 0) {
                            return 0;
                        } else {
                            roomsInUse = roomsInUse + 1;
                        }
                    }
                }
            }
            return facility.getFacilityDetail().getNumberOfRooms() - roomsInUse;
        } catch (Exception se) {
            System.err.println("UseService: Threw an Exception requesting the available capacity of a facility.");
            System.err.println(se.getMessage());
        }
        return 0;
    }
}
