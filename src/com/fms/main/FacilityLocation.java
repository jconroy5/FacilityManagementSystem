package com.fms.main;

public class FacilityLocation {
    private int Address;
    private String Street;
    private String City;
    private String State;
    private int zipCode;
    private String Country;

    FacilityLocation(){}

    public FacilityLocation(int Address, String Street, String City, String State, int zipCode, String Country)
    {
        this.Address = Address;
        this.Street = Street;
        this.City = City;
        this.State = State;
        this.zipCode = zipCode;
        this.Country = Country;
    }

    //getters and setters
    public int getAddress(){
        return Address;
    }
    public void setAddress(int Address){
        this.Address = Address;
    }
    public String getStreet(){
        return Street;
    }
    public void setStreet(String Street){
        this.Street = Street;
    }
    public String getCity(){
        return City;
    }
    public void setCity(String City){
        this.City = City;
    }
    public String getState(){
        return State;
    }
    public void setState(String State){
        this.State = State;
    }
    public int getZipCode(){
        return zipCode;
    }
    public void setZipCode(int zipCode){
        this.zipCode = zipCode;
    }
    public String getCountry(){
        return Country;
    }
    public void setCountry(String Country){
        this.Country = Country;
    }
}
