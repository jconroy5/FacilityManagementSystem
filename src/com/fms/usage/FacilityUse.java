package com.fms.usage;

import com.fms.main.Facility;
import java.time.LocalDate;

public interface FacilityUse {

    //declaring getters and setters
    public void setFacility(Facility facility);
    public Facility getFacility();
    public int getUseID();
    public void setUseID(int useID);
    public int getFacilityID();
    public void setFacilityID(int facilityID);
    public int getRoomNumber();
    public void setRoomNumber(int roomNumber);
    public LocalDate getStartDate();
    public void setStartDate(LocalDate startDate);
    public LocalDate getEndDate();
    public void setEndDate(LocalDate endDate);
}
