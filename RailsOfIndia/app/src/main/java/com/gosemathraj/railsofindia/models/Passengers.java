package com.gosemathraj.railsofindia.models;

import java.io.Serializable;

/**
 * Created by iamsparsh on 18/2/17.
 */

public class Passengers implements Serializable{

    private String currentStatus;
    private String bookingStatus;
    private Integer coachPOsition;

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Integer getCoachPOsition() {
        return coachPOsition;
    }

    public void setCoachPOsition(Integer coachPOsition) {
        this.coachPOsition = coachPOsition;
    }
}
