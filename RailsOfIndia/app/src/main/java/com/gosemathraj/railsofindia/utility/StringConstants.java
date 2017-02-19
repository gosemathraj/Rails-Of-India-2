package com.gosemathraj.railsofindia.utility;

/**
 * Created by iamsparsh on 12/2/17.
 */

public class StringConstants {

    public final static String API_KEY = "5tvesoq6";
    public final static String ROOT_URL = "http://api.railwayapi.com/";

    public final static String Train_No_Or_Name = ROOT_URL + "name_number/";
    public final static String PNR_STATUS = ROOT_URL + "pnr_status/";
    public final static String LIVE_TRAIN_STATUS = ROOT_URL + "live/";
    public final static String TRAIN_ROUTE = ROOT_URL + "route/";
    public final static String SEAT_AVAILABILITY = ROOT_URL + "check_seat/";
    public final static String TRAIN_BETWEEN_STATIONS = ROOT_URL + "between/";
    public final static String SUGGEST_STATIONS = ROOT_URL + "suggest_station/";

    public final static String LOADING_MESSAGE = "loading";
    public final static String INVALID_TRAIN_NUMBER = "Please Enter Valid Train Number";
    public final static String TRAIN_NUMBER_TITLE = "Train Number";
    public static final String EXCEPTION = "Exception";
    public static final String SERVERRESPONSE = "Server Response";

    public static final String CANCELLEDTRAINS = "http://indiarailinfo.com/trains/cancelled";
}
