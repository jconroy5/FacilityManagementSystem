package com.fms.services;

import com.fms.DAL.FacilityDAO;
import com.fms.DAL.UsageDAO;
import com.fms.usage.FacilityUse;
import com.fms.main.Facility;

public class FacilityService {

    private FacilityDAO facDAO = new FacilityDAO();
    private UsageDAO useDAO = new UsageDAO();

    //Add new Facility to DB
    public void addNewFacility(Facility facility) {
        try {
            facDAO.addNewFacility(facility);
        } catch (Exception se) {
            System.err.println("FacilityService: Threw an Exception adding new facility.");
            System.err.println(se.getMessage());
        }
    }

    //gets Facility's info
    public Facility getFacilityInformation(int id) {

        try {
            Facility facility = facDAO.getFacilityInformation(id);
            return facility;
        } catch (Exception se) {
            System.err.println("FacilityService: Threw an Exception retrieving facility.");
            System.err.println(se.getMessage());
        }
        return null;
    }
}
