package com.gosemathraj.railsofindia.models;

import java.io.Serializable;

/**
 * Created by iamsparsh on 19/2/17.
 */

public class Days implements Serializable{

    private String dayCode;
    private String runs;

    public String getDayCode() {
        return dayCode;
    }

    public void setDayCode(String dayCode) {
        this.dayCode = dayCode;
    }

    public String getRuns() {
        return runs;
    }

    public void setRuns(String runs) {
        this.runs = runs;
    }
}
