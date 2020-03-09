package com.opolo.coronatrackerapp.model;

public class CovidTrackerModel {

    private String state;
    private String country;
    private int latestTotalCases;
    private int diffFromPrevious;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    @Override
    public String toString() {
        return "CovidTrackerModel [country=" + country + ", latestTotalCases=" + latestTotalCases + ", state=" + state
                + "]";
    }

    public int getDiffFromPrevious() {
        return diffFromPrevious;
    }

    public void setDiffFromPrevious(int diffFromPrevious) {
        this.diffFromPrevious = diffFromPrevious;
    }

}