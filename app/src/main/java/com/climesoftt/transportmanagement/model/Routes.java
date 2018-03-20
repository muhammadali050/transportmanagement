package com.climesoftt.transportmanagement.model;

import com.climesoftt.transportmanagement.Route;

/**
 * Created by AtoZ on 3/20/2018.
 */

public class Routes {
    private String toCity = "";
    private String fromCity = "";
    private String tooPlaza = "";
    private String petrolCost = "";
    private  String extras = "";
    public Routes()
    {
    }
    public Routes(String to , String from , String tool , String petrol, String extras)
    {
        this.toCity = to;
        this.fromCity = from;
        this.tooPlaza = tool;
        this.petrolCost = petrol;
        this.extras = extras;
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

}
