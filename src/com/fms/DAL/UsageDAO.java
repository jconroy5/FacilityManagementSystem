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
     * Assigns Facility to use by adding it to the use table
     * @param facilityUse instance of FacilityUse to be assigned, indicating room number and start and end dates
     */
    public void assignFacilityToUse(FacilityUse facilityUse) {

        Connection con = DBHelper.getConnection();
        PreparedStatement usePst = null;

        try {
            //Insert the facility ID, room number, and start/end dates into use table
            String useStm = "INSERT INTO use (facility_id, room_number, start_date, "
                    + "end_date) VALUES (?, ?, ?, ?)";
            usePst = con.prepareStatement(useStm);
            usePst.setInt(1, facilityUse.getFacilityID());
            usePst.setInt(2, facilityUse.getRoomNumber());
            usePst.setDate(3, Date.valueOf(facilityUse.getStartDate()));
            usePst.setDate(4, Date.valueOf(facilityUse.getEndDate()));
            usePst.executeUpdate();
            System.out.println("UseDAO: ********** Query " + usePst + "\n");

            //close to manage resources
            usePst.close();
            con.close();
        }
        catch (SQLException se) {
            System.err.println("UseDAO: Threw a SQLException assigning a facility "
                    + "to use in the use table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }
    }


    /**
     * Gets the start date of a given Facility
     * @param facility the Facility whose start date will be retrieved
     * @return Returns the start date of the selected Facility
     */
    public LocalDate getFacilityStartDate(Facility facility) {

        LocalDate facilityStartDate = null;
        try {

            Statement st = DBHelper.getConnection().createStatement();
            String getFacilityStartDateQuery = "SELECT start_date FROM use WHERE facility_id = '" +
                    facility.getFacilityID() + "' ORDER BY start_date LIMIT 1";

            ResultSet useRS = st.executeQuery(getFacilityStartDateQuery);
            System.out.println("UseDAO: ********** Query " + getFacilityStartDateQuery + "\n");

            while ( useRS.next() ) {
                facilityStartDate = useRS.getDate("start_date").toLocalDate();
            }

            //close to manage resources
            useRS.close();
            st.close();
        }
        catch (SQLException se) {
            System.err.println("UseDAO: Threw a SQLException retrieving facility start date "
                    + "from the use table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }
        return facilityStartDate;
    }


    /**
     * Generates list of all usage assignments at a given Facility
     * @param facility The Facility whose usage assignments are being listed
     * @return Returns a list of FacilityUse objects with room number and start and end dates
     */
    public List<FacilityUse> listActualUsage(Facility facility) {

        List<FacilityUse> listOfUsage = new ArrayList<FacilityUse>();

        try {

            Statement st = DBHelper.getConnection().createStatement();
            String listUsageQuery = "SELECT * FROM use WHERE facility_id = '" +
                    facility.getFacilityID() + "' ORDER BY room_number, start_date";

            ResultSet useRS = st.executeQuery(listUsageQuery);
            System.out.println("UseDAO: ********** Query " + listUsageQuery + "\n");

            while ( useRS.next() ) {
                FacilityUse use = new FacilityUseImpl();
                use.setFacilityID(facility.getFacilityID());
                use.setRoomNumber(useRS.getInt("room_number"));
                use.setStartDate(useRS.getDate("start_date").toLocalDate());
                use.setEndDate(useRS.getDate("end_date").toLocalDate());
                listOfUsage.add(use);
            }

            //close to manage resources
            useRS.close();
            st.close();
            return listOfUsage;
        }
        catch (SQLException se) {
            System.err.println("UseDAO: Threw a SQLException retrieving list of usage from use table.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }
        return null;
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


    /**
     * Vacates a Facility or a room by setting end date of current assignment to previous day
     * @param facility the Facility that will be vacated
     * @param roomNumber the room to vacate (if 0, the entire Facility will be vacated
     */
    public void vacateFacility(Facility facility, int roomNumber) {

        try {

            Statement st = DBHelper.getConnection().createStatement();
            String vacateQuery = "";

            List<FacilityUse> usageList = listActualUsage(facility);
            for (FacilityUse use : usageList) {
                if ((use.getRoomNumber() == roomNumber || use.getRoomNumber() == 0) & ((LocalDate.now().equals(use.getStartDate()) ||
                        LocalDate.now().isAfter(use.getStartDate())) & LocalDate.now().equals(use.getEndDate()) ||
                        LocalDate.now().isBefore(use.getEndDate()))) {
                    vacateQuery = "UPDATE use SET end_date = '" + Date.valueOf(LocalDate.now().minusDays(1)) +
                            "' WHERE facility_id = " + facility.getFacilityID() + "AND room_number = " + roomNumber +
                            "AND start_date = '" + Date.valueOf(use.getStartDate()) + "'";
                }
            }

            st.execute(vacateQuery);
            System.out.println("UseDAO: ********** Query " + vacateQuery + "\n");
        }
        catch (SQLException se){
            System.err.println("UseDAO: Threw a SQLException vacating the facility.");
            System.err.println(se.getMessage());
            se.printStackTrace();
        }
    }


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
                FacilityInspection inspection = new FacilityInspectionImpl();
                inspection.setInspection_type(useRS.getString("Inspection Type"));
                inspection.setInspection_detail(useRS.getString("Inspection Details"));
                inspection.setFacilityID(facility.getFacilityID());
                listOfInspections.add(inspection);
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
}
