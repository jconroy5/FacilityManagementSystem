package com.fms.services;

import com.fms.DAL.MaintenanceDAO;
import com.fms.maintenance.Maintenance;
import com.fms.main.Facility;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ArrayList;

public class MaintenanceService {

    private MaintenanceDAO maintenanceDAO = new MaintenanceDAO();

    public Maintenance makeFacilityMaintRequest(Facility facility, String maintenanceDetails, int cost) {
        try {
            return maintenanceDAO.makeFacilityMaintRequest(facility, maintenanceDetails, cost);
        } catch (Exception se) {
            System.err.println("MaintenanceService: Threw an Exception making a "
                    + "maintenance request.");
            System.err.println(se.getMessage());
        }
        return null;
    }

    public void scheduleMaintenance(Maintenance maintenanceRequest) {
        try {
            maintenanceDAO.scheduleMaintenance(maintenanceRequest);
        } catch (Exception se) {
            System.err.println("MaintenanceService: Threw an Exception scheduling maintenance.");
            System.err.println(se.getMessage());
        }
    }

    public int calcMaintenanceCostForFacility(Facility facility) {
        try {
            return maintenanceDAO.calcMaintenanceCostForFacility(facility);
        } catch (Exception se) {
            System.err.println("MaintenanceService: Threw an Exception calculating "
                    + "maintenance cost for facility.");
            System.err.println(se.getMessage());
        }
        return 0;
    }

    //calculate the downtime for a given Facility under maintenance
    public int calcDownTimeForFacility(Facility facility) {
        int daysOfDownTime = 0;
        try {
            //assume each maintenance request takes one work week (5 days) to complete
            int numberOfCompletedMaintItems = maintenanceDAO.listMaintenance(facility).size();
            daysOfDownTime = numberOfCompletedMaintItems * 5;
        } catch (Exception se) {
            System.err.println("MaintenanceService: Threw an Exception calculating "
                    + "the down time for a facility.");
            System.err.println(se.getMessage());
        }
        return daysOfDownTime;
    }

    //problem rate for a given Facility is the down-for-maintenance time divided by the days since the Facility was started
    public double calcProblemRateForFacility(Facility facility) {
        UsageService useService = new UsageService();
        try {
            LocalDate facilityStartDate = useService.getFacilityStartDate(facility);
            double totalDays = ChronoUnit.DAYS.between(facilityStartDate, LocalDate.now());
            return calcDownTimeForFacility(facility) / totalDays;
        } catch (Exception se) {
            System.err.println("MaintenanceService: Threw an Exception calculating "
                    + "the down time for a facility.");
            System.err.println(se.getMessage());
        }
        return 0;
    }

    public List<Maintenance> listMaintRequests(Facility facility) {
        try {
            return maintenanceDAO.listMaintRequests(facility);
        } catch (Exception se) {
            System.err.println("MaintenanceService: Threw an Exception listing "
                    + "maintenance requests.");
            System.err.println(se.getMessage());
        }
        return null;
    }

    public List<Maintenance> listMaintenance(Facility facility) {
        try {
            return maintenanceDAO.listMaintenance(facility);
        } catch (Exception se) {
            System.err.println("MaintenanceService: Threw an Exception listing "
                    + "completed maintenance.");
            System.err.println(se.getMessage());
        }
        return null;
    }
}
