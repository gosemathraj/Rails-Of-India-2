package com.gosemathraj.railsofindia.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by iamsparsh on 19/2/17.
 */

public class TrainNameNumber implements Serializable{

    private String trainName;
    private String trainNumber;
    private List<Days> daysList;

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

    public List<Days> getDaysList() {
        return daysList;
    }

    public void setDaysList(List<Days> daysList) {
        this.daysList = daysList;
    }
}
