package com.climesoftt.transportmanagement.model;

/**
 * Created by AtoZ on 3/24/2018.
 */

public class Maintenance {
    public Maintenance()
    {

    }

    private String id;
    private String startDate;
    private String endDate;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
