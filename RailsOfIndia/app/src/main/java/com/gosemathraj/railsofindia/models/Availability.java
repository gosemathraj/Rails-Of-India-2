package com.gosemathraj.railsofindia.models;

import java.io.Serializable;

/**
 * Created by iamsparsh on 17/2/17.
 */

public class Availability implements Serializable{

    private String date;
    private String status;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
