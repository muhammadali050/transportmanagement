package com.climesoftt.transportmanagement.model;

/**
 * Created by AtoZ on 3/20/2018.
 */

public class Routes {
    private String id = "";
    private String rDate = "";
    private String toCity = "";
    private String fromCity = "";
    private String tooPlaza = "";
    private String petrolCost = "";
    private  String extras = "";
    private String driver = "";
    private String accountType = "";
    private String personName = "";
    private String description = "";
    private String email = "";

    public Routes()
    {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getrDate() {
        return rDate;
    }

    public void setrDate(String rDate) {
        this.rDate = rDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getTooPlaza() {
        return tooPlaza;
    }

    public void setTooPlaza(String tooPlaza) {
        this.tooPlaza = tooPlaza;
    }

    public String getPetrolCost() {
        return petrolCost;
    }

    public void setPetrolCost(String petrolCost) {
        this.petrolCost = petrolCost;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public void setDriver(String driver)
    {
        this.driver = driver;
    }
    public String getDriver()
    {
        return driver;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
