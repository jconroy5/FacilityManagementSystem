package com.fms.services;

import com.fms.DAL.UsageDAO;
import com.fms.usage.*;
import com.fms.main.*;
import java.util.List;

public class UsageService {

    private UsageDAO usageDAO = new UsageDAO();

    public List<FacilityInspection> listInspections(Facility facility) {
        try {
            return usageDAO.listInspections(facility);
        } catch (Exception se) {
            System.err.println("UseService: Threw an Exception retrieving list of inspections.");
            System.err.println(se.getMessage());
        }
        return null;
    }
}
