package com.example.textclock

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.*
import android.os.Handler
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.RemoteViews
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*


val APP_PREFERENCES = "mysettings"

val APP_PREFERENCES_M1 = "M1"
val APP_PREFERENCES_d1 = "d1"
val APP_PREFERENCES_y1 = "y1"
val APP_PREFERENCES_h1 = "h1"
val APP_PREFERENCES_m1 = "m1"
val APP_PREFERENCES_a1 = "a1"

val APP_PREFERENCES_M2 = "M2"
val APP_PREFERENCES_d2 = "d2"
val APP_PREFERENCES_y2 = "y2"
val APP_PREFERENCES_h2 = "h2"
val APP_PREFERENCES_m2 = "m2"
val APP_PREFERENCES_a2 = "a2"

/*val APP_PREFERENCES_M3 = "M3"
val APP_PREFERENCES_h3 = "h3"
val APP_PREFERENCES_a3 = "a3"*/

class ClockWidget : AppWidgetProvider() {

    val UPDATE_ALL_WIDGETS = "update_all_widgets"

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {


        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onEnabled(context: Context) {
        super.onEnabled(context)

        val intent = Intent(context, ClockWidget::class.java)
        intent.action = ACTION_APPWIDGET_UPDATE
        val pIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 60000, pIntent)


        var mSettings: SharedPreferences? = null
        mSettings = context.getSharedPreferences(APP_PREFERENCES, 0)
        val views = RemoteViews(context.packageName, R.layout.clock_widget)
        if(mSettings.contains(APP_PREFERENCES_M1)) {
            var futureMonth = mSettings.getString(APP_PREFERENCES_M1, "DEC")
            views.setTextViewText(R.id.fM, futureMonth);
        }
        if(mSettings.contains(APP_PREFERENCES_d1)) {
            var futureDay = mSettings.getString(APP_PREFERENCES_d1, "26");
            views.setTextViewText(R.id.fd, futureDay)
        }
        if(mSettings.contains(APP_PREFERENCES_y1)) {
            var futureMonth = mSettings.getString(APP_PREFERENCES_y1, "1996")
            views.setTextViewText(R.id.fy, futureMonth)
        }

        if(mSettings.contains(APP_PREFERENCES_h1)) {
            var futureDay = mSettings.getString(APP_PREFERENCES_h1, "10");
            views.setTextViewText(R.id.fh, futureDay)
        }
        if(mSettings.contains(APP_PREFERENCES_m1)) {
            var futureMonth = mSettings.getString(APP_PREFERENCES_m1, "00")
            views.setTextViewText(R.id.fm, futureMonth)
        }



        if(mSettings.contains(APP_PREFERENCES_M2)) {
            var futureMonth = mSettings.getString(APP_PREFERENCES_M2, "DEC")
            views.setTextViewText(R.id.pM, futureMonth)
        }

        if(mSettings.contains(APP_PREFERENCES_d2)) {
            var futureDay = mSettings.getString(APP_PREFERENCES_d2, "26");
            views.setTextViewText(R.id.pd, futureDay)
        }


        if(mSettings.contains(APP_PREFERENCES_y2)) {
            var futureMonth = mSettings.getString(APP_PREFERENCES_y2, "1996")
            views.setTextViewText(R.id.py, futureMonth)
        }

        if(mSettings.contains(APP_PREFERENCES_h2)) {
            var futureDay = mSettings.getString(APP_PREFERENCES_h2, "10");
            views.setTextViewText(R.id.ph, futureDay)
        }
        if(mSettings.contains(APP_PREFERENCES_m2)) {
            var futureMonth = mSettings.getString(APP_PREFERENCES_m2, "00")
            views.setTextViewText(R.id.pm, futureMonth)
        }


        if(mSettings.contains(APP_PREFERENCES_a2)) {
            val a2 = mSettings.getString(APP_PREFERENCES_a2, "PM").toString()

            if (a2 == "AM") {
                views.setViewVisibility(R.id.pastAM, VISIBLE)
                views.setViewVisibility(R.id.pastPM, INVISIBLE)
            }
            if (a2 == "PM") {
                views.setViewVisibility(R.id.pastPM, VISIBLE)
                views.setViewVisibility(R.id.pastAM, INVISIBLE)
            }
        }

        if(mSettings.contains(APP_PREFERENCES_a1)) {
            val a1 = mSettings.getString(APP_PREFERENCES_a1, "PM").toString()

            if (a1 == "AM") {
                views.setViewVisibility(R.id.futureAM, VISIBLE)
                views.setViewVisibility(R.id.futurePM, INVISIBLE)
            }
            if (a1 == "PM") {
                views.setViewVisibility(R.id.futurePM, VISIBLE)
                views.setViewVisibility(R.id.futureAM, INVISIBLE)
            }
        }


        val cal = Calendar.getInstance()
        val formMonth = SimpleDateFormat("MMM", Locale.ENGLISH)
        views.setTextViewText(R.id.rM, formMonth.format(cal.time))

        /*val formHour = SimpleDateFormat("hh")
        views.setTextViewText(R.id.rh, formHour.format(cal.time))*/


        val formMeridiem = SimpleDateFormat("aa")
        val realMeridiem = formMeridiem.format(cal.time)

        if (realMeridiem == "AM") {
            views.setViewVisibility(R.id.realAM, VISIBLE)
            views.setViewVisibility(R.id.realPM, INVISIBLE)
        }

        if (realMeridiem == "PM") {
            views.setViewVisibility(R.id.realPM, VISIBLE)
            views.setViewVisibility(R.id.realAM, INVISIBLE)
        }

    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        val intent = Intent(context, ClockWidget::class.java)
        intent.action = UPDATE_ALL_WIDGETS
        val pIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pIntent)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(
            context?.let {
                ComponentName(
                    it,
                    ClockWidget::class.java
                )
            }
        )
        onUpdate(context!!, appWidgetManager, appWidgetIds)

    }
}

@SuppressLint("SimpleDateFormat")
internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    /*@SuppressLint("ShowToast") val toast = Toast.makeText(context, "onUpdate", Toast.LENGTH_LONG)
    toast.show()*/
    val views = RemoteViews(context.packageName, R.layout.clock_widget)

    var mSettings: SharedPreferences? = null
    mSettings = context.getSharedPreferences(APP_PREFERENCES, 0)


    if(mSettings.contains(APP_PREFERENCES_M1)) {
        var futureMonth = mSettings.getString(APP_PREFERENCES_M1, "DEC")
        views.setTextViewText(R.id.fM, futureMonth)
    }

    if(mSettings.contains(APP_PREFERENCES_d1)) {
        var futureDay = mSettings.getString(APP_PREFERENCES_d1, "26");
        views.setTextViewText(R.id.fd, futureDay)
    }


    if(mSettings.contains(APP_PREFERENCES_y1)) {
        var futureMonth = mSettings.getString(APP_PREFERENCES_y1, "1996")
        views.setTextViewText(R.id.fy, futureMonth)
    }

    if(mSettings.contains(APP_PREFERENCES_h1)) {
        var futureDay = mSettings.getString(APP_PREFERENCES_h1, "10");
        views.setTextViewText(R.id.fh, futureDay)
    }
    if(mSettings.contains(APP_PREFERENCES_m1)) {
        var futureMonth = mSettings.getString(APP_PREFERENCES_m1, "00")
        views.setTextViewText(R.id.fm, futureMonth)
    }



    if(mSettings.contains(APP_PREFERENCES_M2)) {
        var futureMonth = mSettings.getString(APP_PREFERENCES_M2, "DEC")
        views.setTextViewText(R.id.pM, futureMonth)
    }

    if(mSettings.contains(APP_PREFERENCES_d2)) {
        var futureDay = mSettings.getString(APP_PREFERENCES_d2, "26");
        views.setTextViewText(R.id.pd, futureDay)
    }


    if(mSettings.contains(APP_PREFERENCES_y2)) {
        var futureMonth = mSettings.getString(APP_PREFERENCES_y2, "1996")
        views.setTextViewText(R.id.py, futureMonth)
    }

    if(mSettings.contains(APP_PREFERENCES_h2)) {
        var futureDay = mSettings.getString(APP_PREFERENCES_h2, "10");
        views.setTextViewText(R.id.ph, futureDay)
    }
    if(mSettings.contains(APP_PREFERENCES_m2)) {
        var futureMonth = mSettings.getString(APP_PREFERENCES_m2, "00")
        views.setTextViewText(R.id.pm, futureMonth)
    }

    if(mSettings.contains(APP_PREFERENCES_a2)) {
        val a2 = mSettings.getString(APP_PREFERENCES_a2, "PM").toString()

        if (a2 == "AM") {
            views.setViewVisibility(R.id.pastAM, VISIBLE)
            views.setViewVisibility(R.id.pastPM, INVISIBLE)
        }
        if (a2 == "PM") {
            views.setViewVisibility(R.id.pastPM, VISIBLE)
            views.setViewVisibility(R.id.pastAM, INVISIBLE)
        }
    }

    if(mSettings.contains(APP_PREFERENCES_a1)) {
        val a1 = mSettings.getString(APP_PREFERENCES_a1, "PM").toString()

        if (a1 == "AM") {
            views.setViewVisibility(R.id.futureAM, VISIBLE)
            views.setViewVisibility(R.id.futurePM, INVISIBLE)
        }
        if (a1 == "PM") {
            views.setViewVisibility(R.id.futurePM, VISIBLE)
            views.setViewVisibility(R.id.futureAM, INVISIBLE)
        }
    }

    val cal = Calendar.getInstance()
    val formMonth = SimpleDateFormat("MMM", Locale.ENGLISH)
    views.setTextViewText(R.id.rM, formMonth.format(cal.time))

    /*val formHour = SimpleDateFormat("hh")
    views.setTextViewText(R.id.rh, formHour.format(cal.time))*/


    val formMeridiem = SimpleDateFormat("aa")
    val realMeridiem = formMeridiem.format(cal.time)

    if (realMeridiem == "AM") {
        views.setViewVisibility(R.id.realAM, VISIBLE)
        views.setViewVisibility(R.id.realPM, INVISIBLE)
    }

    if (realMeridiem == "PM") {
        views.setViewVisibility(R.id.realPM, VISIBLE)
        views.setViewVisibility(R.id.realAM, INVISIBLE)
    }

    appWidgetManager.updateAppWidget(appWidgetId, views)
}