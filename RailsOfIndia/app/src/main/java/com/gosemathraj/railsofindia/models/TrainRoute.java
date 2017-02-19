package com.gosemathraj.railsofindia.models;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by iamsparsh on 17/2/17.
 */

public class TrainRoute implements Serializable{

    private Integer responseCode;
    private String trainName;
    private String trainNumber;
    private List<Route> route;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public List<Route> getRoute() {
        return route;
    }

    public void setRoute(List<Route> route) {
        this.route = route;
    }


}
