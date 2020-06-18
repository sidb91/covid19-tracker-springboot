package com.opolo.coronatrackerapp.model;

public class CovidRecovTrackerModel {

    private String state;
    private String country;
    private String latitude;
    private String longitude;
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

    @Override
    public String toString() {
        return "CovidRecovTrackerModel [country=" + country + ", diffFromPrevious=" + diffFromPrevious
                + ", latestTotalCases=" + latestTotalCases + ", latitude=" + latitude + ", longitude=" + longitude
                + ", state=" + state + "]";
    }

}
