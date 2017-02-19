package com.gosemathraj.railsofindia.data;

import android.content.ContentUris;
import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by iamsparsh on 16/2/17.
 */

public class Contract {

    public final static String AUTHORITY = "com.gosemathraj.railsofindia.data.dataprovider";
    public final static Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public final static String PATH_PNR = "pnr";

    public static final class PnrEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PNR).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI  + "/" + PATH_PNR;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_PNR;

        public static final String TABLE_NAME = "PNR_TABLE";
        public static final String COLUMN_TRAIN_START_DATE = "trainStartDate";
        public static final String COLUMN_TRAIN_CLASS = "class";
        public static final String COLUMN_TOTAL_PASSENGERS = "totalPassengers";
        public static final String COLUMN_TRAIN_NAME = "trainName";
        public static final String COLUMN_RESERVATION_UPTO = "reservationUpto";
        public static final String COLUMN_SOURCE_STATION = "sourceStation";
        public static final String COLUMN_DESTINATION_STATION = "destinationStation";
        public static final String COLUMN_BOARDING_STATION = "boardingStation";
        public static final String COLUMN_PASSENGERS = "passengers";
        public static final String COLUMN_CHART_PREPARED = "chartPrepared";
        public static final String COLUMN_PNR_NUMBER = "pnrNumber";
        public static final String COLUMN_TRAIN_NUMBER = "trainNumber";


        public static Uri buildPnrUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
