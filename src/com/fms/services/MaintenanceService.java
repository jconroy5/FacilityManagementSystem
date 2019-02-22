package com.fms.services;

import com.fms.DAL.MaintenanceDAO;
import com.fms.maintenance.Maintenance;
import com.fms.main.Facility;

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
}
