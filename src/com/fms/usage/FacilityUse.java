package com.fms.usage;

import java.time.LocalDate;

public class FacilityUse {

    //room number within Facility and Facility's start and end dates of operation
    private int roomNumber;
    private LocalDate startDate;
    private LocalDate endDate;

    public FacilityUse() {}

    //getters and setters
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
