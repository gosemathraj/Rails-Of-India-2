package com.gosemathraj.railsofindia.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;

import com.gosemathraj.railsofindia.R;
import com.gosemathraj.railsofindia.activities.MainActivity;
import com.gosemathraj.railsofindia.data.Contract;
import com.gosemathraj.railsofindia.utility.Utils;

/**
 * Created by iamsparsh on 18/2/17.
 */

public class WidgetProvider extends AppWidgetProvider {

    private Cursor cursordata;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        final int N = appWidgetIds.length;
        for (int i=0; i<N; i++) {

            cursordata = Utils.getInstance().getDataCursor(context);

            if(cursordata != null){
                cursordata.moveToLast();
            }
            int appWidgetId = appWidgetIds[i];

            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_pnr_widget);

            views.setTextViewText(R.id.train_name,cursordata.getString(cursordata.getColumnIndex(Contract.PnrEntry.COLUMN_TRAIN_NAME)));
            views.setTextViewText(R.id.train_number,cursordata.getString(cursordata.getColumnIndex(Contract.PnrEntry.COLUMN_TRAIN_NUMBER)));
            views.setTextViewText(R.id.source,cursordata.getString(cursordata.getColumnIndex(Contract.PnrEntry.COLUMN_SOURCE_STATION)));
            views.setTextViewText(R.id.destination,cursordata.getString(cursordata.getColumnIndex(Contract.PnrEntry.COLUMN_DESTINATION_STATION)));
            views.setTextViewText(R.id.reserved_upto,cursordata.getString(cursordata.getColumnIndex(Contract.PnrEntry.COLUMN_RESERVATION_UPTO)));

            views.setTextViewText(R.id.booking_status, Utils.getInstance().getPassengersString(
                    cursordata.getString(cursordata.getColumnIndex(Contract.PnrEntry.COLUMN_PASSENGERS))
            ));
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            cursordata.close();

            appWidgetManager.updateAppWidget(appWidgetId, views);

        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }
}
