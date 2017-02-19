package com.gosemathraj.railsofindia.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by iamsparsh on 16/2/17.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String NAME = "RailsOfIndia.db";
    private static final int VERSION = 1;

    public DbHelper(Context context) {
        super(context,NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createPnrTable(sqLiteDatabase);
    }

    private void createPnrTable(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + Contract.PnrEntry.TABLE_NAME + "(" +
                Contract.PnrEntry.COLUMN_PNR_NUMBER + " INTEGER PRIMARY KEY, " +
                Contract.PnrEntry.COLUMN_BOARDING_STATION + " TEXT NOT NULL, " +
                Contract.PnrEntry.COLUMN_CHART_PREPARED + " TEXT NOT NULL, " +
                Contract.PnrEntry.COLUMN_TRAIN_NUMBER + " TEXT NOT NULL, " +
                Contract.PnrEntry.COLUMN_PASSENGERS + " TEXT NOT NULL, " +
                Contract.PnrEntry.COLUMN_SOURCE_STATION + " TEXT NOT NULL, " +
                Contract.PnrEntry.COLUMN_DESTINATION_STATION + " TEXT NOT NULL, " +
                Contract.PnrEntry.COLUMN_RESERVATION_UPTO + " TEXT NOT NULL, " +
                Contract.PnrEntry.COLUMN_TRAIN_NAME + " TEXT NOT NULL, " +
                Contract.PnrEntry.COLUMN_TOTAL_PASSENGERS + " INTEGER NOT NULL, " +
                Contract.PnrEntry.COLUMN_TRAIN_CLASS + " TEXT NOT NULL, " +
                Contract.PnrEntry.COLUMN_TRAIN_START_DATE+ " TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + Contract.PnrEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
