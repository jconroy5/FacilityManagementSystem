package com.fms.DAL;

import com.fms.main.Facility;
import com.fms.usage.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Modeled after CustomerDAO.java from BookStore, COMP 373/473, Spring 2019.
 */
public class UsageDAO {

    public UsageDAO() {}

    /**
     * Generates a list of inspections at a given Facility
     * @param facility the facility that will be searched for inspections
     * @return returns listOfInspections
     */
    public List<FacilityInspection> listInspections(Facility facility) {

        List<FacilityInspection> listOfInspections = new ArrayList<FacilityInspection>();

        try {
            Statement st = DBHelper.getConnection().createStatement();
            String listInspectionsQuery = "SELECT * FROM inspection WHERE "
                    + "facility_id = '" + facility.getFacilityID() + "'";

            ResultSet useRS = st.executeQuery(listInspectionsQuery);
            System.out.println("UseDAO: ********** Query " + listInspectionsQuery + "\n");

            while ( useRS.next() ) {
                FacilityInspection inspec = new FacilityInspection();
                inspec.setInspection_type(useRS.getString("inspection_type"));
                inspec.setInspection_detail(useRS.getString("inspection_detail"));
                inspec.setFacility_ID(facility.getFacilityID());
                listOfInspections.add(inspec);
            }

            //close to manage resources
            useRS.close();
            st.close();
        }
        catch (SQLException se) {
            System.err.println("UseDAO: Threw a SQLException retrieving "
                    + "inspections from Inspections table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }
        return listOfInspections;
    }

    /**
     * Checks if given Facility is in use given a specific interval of time, by room number
     * @param facilityUse
     * @return Returns true if a Facility or room is in use, returns false otherwise
     */
    public boolean isInUseDuringInterval(FacilityUse facilityUse) {

        //default is false
        boolean result = false;
        try {
            //Takes in Facility ID, room number, and start and end dates into use table
            Statement st = DBHelper.getConnection().createStatement();
            String selectUseAssignments = "SELECT * FROM use WHERE facility_id = " + facilityUse.getFacilityID() +
                    " AND room_number IN (0, " + facilityUse.getRoomNumber() + ")";

            ResultSet useRS = st.executeQuery(selectUseAssignments);
            System.out.println("UseDAO: ********** Query " + selectUseAssignments + "\n");

            //checks if the DB dates overlap with the input interval
            while (useRS.next()) {
                LocalDate assignStart = useRS.getDate("start_date").toLocalDate();
                LocalDate assignEnd = useRS.getDate("end_date").toLocalDate();
                if (facilityUse.getStartDate().isBefore(assignEnd) && (assignStart.isBefore(facilityUse.getEndDate()) ||
                        assignStart.equals(facilityUse.getEndDate()))) {
                    result = true;
                    break;
                }
            }

            //close to manage resources
            useRS.close();
            st.close();
        }
        catch (SQLException se) {
            System.err.println("UseDAO: Threw a SQLException checking if "
                    + "facility is in use during an interval.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }
        return result;
    }
}
