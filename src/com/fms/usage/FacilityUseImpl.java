package com.fms.usage;

import com.fms.main.Facility;
import java.time.LocalDate;

public class FacilityUseImpl implements FacilityUse{
    private int useID;
    private int facilityID;
    private int roomNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private Facility facility;

    public FacilityUseImpl() {}

    public Facility getFacility() {
        return facility;
    }
    public void setFacility(Facility facility) {
        this.facility = facility;
    }
    public int getUseID() {
        return useID;
    }
    public void setUseID(int useID) {
        this.useID = useID;
    }
    public int getFacilityID() {
        return facilityID;
    }
    public void setFacilityID(int facilityID) {
        this.facilityID = facilityID;
    }
    public int getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}