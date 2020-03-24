package com.opolo.coronatrackerapp.model;

public class CovidTrackerModel {

    private String state;
    private String country;
    private String latitude;
    private String longitude;
    private int latestTotalCases;
    private int diffFromPrevious;
    private int totalDeathCases;
    private int diffInDeathFrPrev;
    private int totalRecovCases;
    private int diffInRecovCases;
    
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

    public int getDiffFromPrevious() {
        return diffFromPrevious;
    }

    public void setDiffFromPrevious(int diffFromPrevious) {
        this.diffFromPrevious = diffFromPrevious;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getTotalDeathCases() {
        return totalDeathCases;
    }

    public void setTotalDeathCases(int totalDeathCases) {
        this.totalDeathCases = totalDeathCases;
    }

    public int getDiffInDeathFrPrev() {
        return diffInDeathFrPrev;
    }

    public void setDiffInDeathFrPrev(int diffInDeathFrPrev) {
        this.diffInDeathFrPrev = diffInDeathFrPrev;
    }

    @Override
    public String toString() {
        return "CovidTrackerModel [country=" + country + ", diffFromPrevious=" + diffFromPrevious
                + ", diffInDeathFrPrev=" + diffInDeathFrPrev + ", latestTotalCases=" + latestTotalCases + ", latitude="
                + latitude + ", longitude=" + longitude + ", state=" + state + ", totalDeathCases=" + totalDeathCases
                + "]";
    }

    public int getTotalRecovCases() {
        return totalRecovCases;
    }

    public void setTotalRecovCases(int totalRecovCases) {
        this.totalRecovCases = totalRecovCases;
    }

    public int getDiffInRecovCases() {
        return diffInRecovCases;
    }

    public void setDiffInRecovCases(int diffInRecovCases) {
        this.diffInRecovCases = diffInRecovCases;
    }

}