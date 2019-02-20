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

public class UsageDAO {

    public UsageDAO() {}

    public List<FacilityInspection> listInspections(Facility fac) {

        List<FacilityInspection> listOfInspections = new ArrayList<FacilityInspection>();

        try {
            Statement st = DBHelper.getConnection().createStatement();
            String listInspectionsQuery = "SELECT * FROM inspection WHERE "
                    + "facility_id = '" + fac.getFacilityID() + "'";

            ResultSet useRS = st.executeQuery(listInspectionsQuery);
            System.out.println("UseDAO: ********** Query " + listInspectionsQuery + "\n");

            while ( useRS.next() ) {
                FacilityInspection inspec = new FacilityInspection();
                inspec.setInspection_type(useRS.getString("inspection_type"));
                inspec.setInspection_detail(useRS.getString("inspection_detail"));
                inspec.setFacility_ID(fac.getFacilityID());
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
}
