package com.gosemathraj.railsofindia.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by iamsparsh on 17/2/17.
 */

public class PnrStatus implements Serializable {

    public String trainStartDate;
    public String classLevel;
    public Integer totalPassengers;
    public String trainName;
    public String reservationUpto;
    public String sourceStation;
    public String destinationStation;
    public String boardingStation;
    public List<Passengers> passengers;
    public String chartPrepared;
    public String pnrNumber;
    public String trainNumber;

    public String getTrainStartDate() {
        return trainStartDate;
    }

    public void setTrainStartDate(String trainStartDate) {
        this.trainStartDate = trainStartDate;
    }

    public String getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(String classLevel) {
        this.classLevel = classLevel;
    }

    public Integer getTotalPassengers() {
        return totalPassengers;
    }

    public void setTotalPassengers(Integer totalPassengers) {
        this.totalPassengers = totalPassengers;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getReservationUpto() {
        return reservationUpto;
    }

    public void setReservationUpto(String reservationUpto) {
        this.reservationUpto = reservationUpto;
    }

    public String getSourceStation() {
        return sourceStation;
    }

    public void setSourceStation(String sourceStation) {
        this.sourceStation = sourceStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }

    public String getBoardingStation() {
        return boardingStation;
    }

    public void setBoardingStation(String boardingStation) {
        this.boardingStation = boardingStation;
    }

    public List<Passengers> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passengers> passengers) {
        this.passengers = passengers;
    }

    public String getChartPrepared() {
        return chartPrepared;
    }

    public void setChartPrepared(String chartPrepared) {
        this.chartPrepared = chartPrepared;
    }

    public String getPnrNumber() {
        return pnrNumber;
    }

    public void setPnrNumber(String pnrNumber) {
        this.pnrNumber = pnrNumber;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }
}
