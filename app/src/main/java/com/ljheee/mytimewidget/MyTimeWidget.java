package com.ljheee.mytimewidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class MyTimeWidget extends AppWidgetProvider {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    // 启动ExampleAppWidgetService服务对应的action
    private final Intent TIME_SERVICE_INTENT =
            new Intent("android.appwidget.action.MY_APP_WIDGET_SERVICE");

    // 更新 widget 的广播对应的action
    private final String ACTION_UPDATE_ALL = "com.ljheee.widget.UPDATE_TIME";

    int[] mAppWidgetIds = new int[2];

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_time_widget);
        views.setTextViewText(R.id.appwidget_text, sdf.format(new Date()));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        final String action = intent.getAction();
        if (ACTION_UPDATE_ALL.equals(action)) {
            // “更新”广播
//            updateAppWidget(context, AppWidgetManager.getInstance(context));
            onUpdate(context,AppWidgetManager.getInstance(context), mAppWidgetIds);
        }

        super.onReceive(context, intent);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        int i = 0;
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            mAppWidgetIds[i++] = appWidgetId;
        }
    }

    @Override
    public void onEnabled(Context context) {
        // 在第一个 widget 被创建时，开启服务
        context.startService(TIME_SERVICE_INTENT);
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        // 在最后一个 widget 被删除时，终止服务
        context.stopService(TIME_SERVICE_INTENT);
        super.onDisabled(context);
    }
}

