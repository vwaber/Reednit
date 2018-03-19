package com.reednit.android.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.reednit.android.R;

import java.util.Locale;

public class TimeKeeperWidgetProvider extends AppWidgetProvider{

    private final TimeKeeper mTimeKeeper = TimeKeeper.getInstance();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {

            long seconds = mTimeKeeper.getTime(context) / 1000;
            int hours = (int) seconds/3600;
            int minutes = (int) seconds/60;
            minutes -= hours * 60;

            String time = String.format(Locale.getDefault(), "%d:%02d", hours, minutes);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.timekeeper_widget);
            remoteViews.setTextViewText(R.id.tv_time, time);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

        }

    }

    public static void forceUpdate(Activity activity){
        Intent intent = new Intent(activity, TimeKeeperWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(activity.getApplication())
                .getAppWidgetIds(new ComponentName(activity.getApplication(), TimeKeeperWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        activity.sendBroadcast(intent);
    }

}
