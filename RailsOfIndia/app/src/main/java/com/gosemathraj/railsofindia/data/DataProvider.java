package com.gosemathraj.railsofindia.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by iamsparsh on 16/2/17.
 */

public class DataProvider extends ContentProvider {

    private DbHelper dbHelper = null;
    private final static int PNR = 1;
    private final static int PNR_ID = 2;

    private final static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(Contract.AUTHORITY, Contract.PATH_PNR, PNR);
        uriMatcher.addURI(Contract.AUTHORITY, Contract.PATH_PNR + "#", PNR_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String order) {

        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor dataCursor;

        switch (uriMatcher.match(uri)){

            case PNR :
                dataCursor = db.query(
                        Contract.PnrEntry.TABLE_NAME,
                                        projection,
                                        selection,
                                        selectionArgs,
                                        null,
                                        null,
                                        order);
                break;

            case PNR_ID :
                long _id = ContentUris.parseId(uri);
                dataCursor = db.query(
                        Contract.PnrEntry.TABLE_NAME,
                                        projection,
                                        Contract.PnrEntry._ID + " =?",
                                        new String[]{String.valueOf(_id)},
                                        null,
                                        null,
                                        order);
                break;

            default :
                throw new UnsupportedOperationException("Unknown Uri" + uri);
        }

        dataCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return dataCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch(uriMatcher.match(uri)){

            case PNR:
                return Contract.PnrEntry.CONTENT_TYPE;

            case PNR_ID:
                return Contract.PnrEntry.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown Uri" +uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        long _id;
        Uri returnUri = null;

        switch(uriMatcher.match(uri)){

            case PNR:
                if(checkIfDataExist(contentValues.get(Contract.PnrEntry.COLUMN_PNR_NUMBER).toString(),db)){

                    _id = db.insert(Contract.PnrEntry.TABLE_NAME, null, contentValues);
                    if(_id > 0){
                        returnUri =  Contract.PnrEntry.buildPnrUri(_id);
                    } else{
                        throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                    }
                }

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    private boolean checkIfDataExist(String pnrNumber,SQLiteDatabase db) {

        Cursor cursor = null;
        String sql ="SELECT " + Contract.PnrEntry.COLUMN_PNR_NUMBER + " FROM "+Contract.PnrEntry.TABLE_NAME+" WHERE " + Contract.PnrEntry.COLUMN_PNR_NUMBER +" = " +pnrNumber;
        cursor= db.rawQuery(sql,null);

        if(cursor.getCount()>0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows;

        switch(uriMatcher.match(uri)){

            case PNR:
                rows = db.delete(Contract.PnrEntry.TABLE_NAME, s, strings);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        if(s == null || rows != 0){
            getContext().getContentResolver().notifyChange(uri, null);

        }

        return rows;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows;

        switch(uriMatcher.match(uri)){

            case PNR:
                rows = db.update(Contract.PnrEntry.TABLE_NAME, contentValues, s, strings);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(rows != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rows;
    }
}
