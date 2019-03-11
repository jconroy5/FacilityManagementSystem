package com.fms.main;

public class FacilityUser {
    private String userName;    //employee's name
    private String userTitle;   //employee's job title
    private long userPhoneNumber;   //employee's phone number
    private String userEmail;   //employee's email address

    FacilityUser(){}

    public FacilityUser(String userID, String userName, long userPhone, String userEmail, String userRole) {
        this.userName = userName;
        this.userTitle = userTitle;
        this.userPhoneNumber = userPhoneNumber;
        this.userEmail = userEmail;
    }

    //getters and setters
    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getUserTitle() {
        return userTitle;
    }
    public void setUserTitle(String userTitle){
        this.userEmail = userEmail;
    }
    public long getUserPhoneNumber() {
        return userPhoneNumber;
    }
    public void setUserPhoneNumber(long userPhoneNumber){
        this.userPhoneNumber = userPhoneNumber;
    }
    public String getUserEmail(){
        return userEmail;
    }
    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }
}
