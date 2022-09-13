package com.example.textclock

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.content.res.ResourcesCompat
import java.text.SimpleDateFormat
import java.util.*



var i = 0
var  arr:Array<String> = arrayOf("DEC", "26", "1996","05", "00","PM")

class MainActivity : AppCompatActivity() {

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

/*    val APP_PREFERENCES_M3 = "M3"
    val APP_PREFERENCES_h3 = "h3"
    val APP_PREFERENCES_a3 = "a3"*/

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mSettings: SharedPreferences? = null
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        if (i==0) i = uploadDates(mSettings)

        val bitmap: Bitmap = (ResourcesCompat.getDrawable(resources,R.drawable.jaba,null) as BitmapDrawable).bitmap
        val imageV = findViewById<ImageView>(R.id.imageView)
        imageV.setImageBitmap(bitmap)

        val timerTask = object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    getPresentDate()
                }
            }
        }
        val timer: Timer = Timer()
        timer.schedule(timerTask, 0, 1000)

        val btn = findViewById<Button>(R.id.button)


        btn.setOnClickListener {
            val a = "future"
            val intent = Intent(this@MainActivity, CalendarActivity::class.java)
            intent.putExtra("typeTime", a)
            startActivity(intent)
        }

        val incIntent = intent

        if(incIntent.getStringExtra("tt") == "future")
            selectFutureDate(arr)

        //AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)

    }

    private fun uploadDates(mSettings : SharedPreferences): Int {
        if(mSettings.contains(APP_PREFERENCES_M1)) {
            val futureMonth = findViewById<TextView>(R.id.futureMonth)
            futureMonth.text = mSettings.getString(APP_PREFERENCES_M1, "DEC")
            arr[0] = futureMonth.text.toString()
        }
        if(mSettings.contains(APP_PREFERENCES_d1)) {
            val futureDay = findViewById<TextView>(R.id.futureDay)
            futureDay.text = mSettings.getString(APP_PREFERENCES_d1, "26");
            arr[1] = futureDay.text.toString()
        }
        if(mSettings.contains(APP_PREFERENCES_y1)) {
            val futureYear = findViewById<TextView>(R.id.futureYear)
            futureYear.text = mSettings.getString(APP_PREFERENCES_y1, "1996")
            arr[2] = futureYear.text.toString()
        }
        if(mSettings.contains(APP_PREFERENCES_h1)) {
            val futureHour = findViewById<TextView>(R.id.futureHour)
            futureHour.text = mSettings.getString(APP_PREFERENCES_h1, "08")
            arr[3] = futureHour.text.toString()
        }
        if(mSettings.contains(APP_PREFERENCES_m1)) {
            val futureMinute = findViewById<TextView>(R.id.futureMinute)
            futureMinute.text = mSettings.getString(APP_PREFERENCES_m1, "10")
            arr[4] = futureMinute.text.toString()
        }

        if(mSettings.contains(APP_PREFERENCES_a1)) {
            val a1 = mSettings.getString(APP_PREFERENCES_a1, "PM").toString()
            val futureAM = findViewById<ImageView>(R.id.futureAM)
            val futurePM = findViewById<ImageView>(R.id.futurePM)

            if (a1 == "AM") {
                futureAM.visibility = View.VISIBLE
                futurePM.visibility = View.INVISIBLE
            }
            if (a1 == "PM") {
                futurePM.visibility = View.VISIBLE
                futureAM.visibility = View.INVISIBLE
            }
            arr[5] = a1
        }


        if(mSettings.contains(APP_PREFERENCES_M2)) {
            val pastMonth = findViewById<TextView>(R.id.pastMonth)
            pastMonth.text = mSettings.getString(APP_PREFERENCES_M2, "DEC")
        }
        if(mSettings.contains(APP_PREFERENCES_d2)) {
            val pastDay = findViewById<TextView>(R.id.pastDay)
            pastDay.text = mSettings.getString(APP_PREFERENCES_d2, "26");
        }
        if(mSettings.contains(APP_PREFERENCES_y2)) {
            val pastYear = findViewById<TextView>(R.id.pastYear)
            pastYear.text = mSettings.getString(APP_PREFERENCES_y2, "1996")
        }
        if(mSettings.contains(APP_PREFERENCES_h2)) {
            val pastHour = findViewById<TextView>(R.id.pastHour)
            pastHour.text = mSettings.getString(APP_PREFERENCES_h2, "08")
        }
        if(mSettings.contains(APP_PREFERENCES_m2)) {
            val pastMinute = findViewById<TextView>(R.id.pastMinute)
            pastMinute.text = mSettings.getString(APP_PREFERENCES_m2, "10")
        }

        if(mSettings.contains(APP_PREFERENCES_a2)) {
            val a2 = mSettings.getString(APP_PREFERENCES_a2, "PM").toString()

            val pastAM = findViewById<ImageView>(R.id.pastAM)
            val pastPM = findViewById<ImageView>(R.id.pastPM)

            if (a2 == "AM") {
                pastAM.visibility = View.VISIBLE
                pastPM.visibility = View.INVISIBLE
            }
            if (a2 == "PM") {
                pastPM.visibility = View.VISIBLE
                pastAM.visibility = View.INVISIBLE
            }
        }

        return 1
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val pastMonth = findViewById<TextView>(R.id.pastMonth)
        val pastDay = findViewById<TextView>(R.id.pastDay)
        val pastYear = findViewById<TextView>(R.id.pastYear)
        val pastHour = findViewById<TextView>(R.id.pastHour)
        val pastMinute = findViewById<TextView>(R.id.pastMinute)
        val pastAM = findViewById<ImageView>(R.id.pastAM)
        editor.putString(APP_PREFERENCES_M2, pastMonth.text.toString())
        editor.putString(APP_PREFERENCES_d2, pastDay.text.toString())
        editor.putString(APP_PREFERENCES_y2, pastYear.text.toString())
        editor.putString(APP_PREFERENCES_h2, pastHour.text.toString())
        editor.putString(APP_PREFERENCES_m2, pastMinute.text.toString())
        var aa2 = ""
        if (pastAM.visibility == View.VISIBLE) {
            aa2 = "AM"
        } else {
            aa2 = "PM"
        }
        editor.putString(APP_PREFERENCES_a2, aa2)

        val futureMonth = findViewById<TextView>(R.id.futureMonth)
        val futureDay = findViewById<TextView>(R.id.futureDay)
        val futureYear = findViewById<TextView>(R.id.futureYear)
        val futureHour = findViewById<TextView>(R.id.futureHour)
        val futureMinute = findViewById<TextView>(R.id.futureMinute)
        val futureAM = findViewById<ImageView>(R.id.futureAM)
        editor.putString(APP_PREFERENCES_M1, futureMonth.text.toString())
        editor.putString(APP_PREFERENCES_d1, futureDay.text.toString())
        editor.putString(APP_PREFERENCES_y1, futureYear.text.toString())
        editor.putString(APP_PREFERENCES_h1, futureHour.text.toString())
        editor.putString(APP_PREFERENCES_m1, futureMinute.text.toString())
        var aa1 = ""
        if (futureAM.visibility == View.VISIBLE) {
            aa1 = "AM"
        } else {
            aa1 = "PM"
        }
        editor.putString(APP_PREFERENCES_a1, aa1)


        /*val realAM = findViewById<ImageView>(R.id.realAM)
        val realMonth = findViewById<TextView>(R.id.realMonth)
        val realHour = findViewById<TextView>(R.id.realHour)
        editor.putString(APP_PREFERENCES_M3, realMonth.text.toString())
        editor.putString(APP_PREFERENCES_h3, realHour.text.toString())
        var aa3 = ""
        if (realAM.visibility == View.VISIBLE) {
            aa3 = "AM"
        } else {
            aa3 = "PM"
        }
        editor.putString(APP_PREFERENCES_a3, aa3)*/

        editor.apply();
    }

    @RequiresApi(Build.VERSION_CODES.O)
   fun selectFutureDate(arr: Array<String>) {
        val month = intent.getStringExtra("month")
        val futureMonth = findViewById<TextView>(R.id.futureMonth)

        if (month != null) {
            futureMonth.text = month
        }

        val day = intent.getStringExtra("day")
        val futureDay = findViewById<TextView>(R.id.futureDay)

        if (day != null) {
            futureDay.text = day
        }

        val year = intent.getStringExtra("year")
        val futureYear = findViewById<TextView>(R.id.futureYear)

        if (year != null) {
            futureYear.text = year
        }

        val hour = intent.getStringExtra("hour")

        val futureHour = findViewById<TextView>(R.id.futureHour)

        if (hour != null) futureHour.text = hour

        val minute = intent.getStringExtra("minute")
        val futureMinute = findViewById<TextView>(R.id.futureMinute)
        if (minute != null)  futureMinute.text = minute

        val futureMeridien = intent.getStringExtra("meridien")

        val futureAM = findViewById<ImageView>(R.id.futureAM)
        val futurePM = findViewById<ImageView>(R.id.futurePM)

        if (futureMeridien == "AM") {
            futureAM.visibility = View.VISIBLE
            futurePM.visibility = View.INVISIBLE
        }

        if (futureMeridien == "PM") {
            futurePM.visibility = View.VISIBLE
            futureAM.visibility = View.INVISIBLE
        }

        val pastMonth = findViewById<TextView>(R.id.pastMonth)
        pastMonth.text = arr[0]

        val pastDay = findViewById<TextView>(R.id.pastDay)
        pastDay.text = arr[1]

        val pastYear = findViewById<TextView>(R.id.pastYear)
        pastYear.text = arr[2]

        val pastHour = findViewById<TextView>(R.id.pastHour)
        pastHour.text = arr[3]

        val pastMinute = findViewById<TextView>(R.id.pastMinute)
        pastMinute.text = arr[4]

        val pastMeridien = arr[5]

        val pastAM = findViewById<ImageView>(R.id.pastAM)
        val pastPM = findViewById<ImageView>(R.id.pastPM)

        if (pastMeridien == "AM") {
            pastAM.visibility = View.VISIBLE
            pastPM.visibility = View.INVISIBLE
        }

        if (pastMeridien == "PM") {
            pastPM.visibility = View.VISIBLE
            pastAM.visibility = View.INVISIBLE
        }


        arr[0] = intent.getStringExtra("month").toString()
        arr[1] = intent.getStringExtra("day").toString()
        arr[2] = intent.getStringExtra("year").toString()
        arr[3] = intent.getStringExtra("hour").toString()
        arr[4] = intent.getStringExtra("minute").toString()
        arr[5] = intent.getStringExtra("meridien").toString()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    fun getPresentDate() {
        val cal = Calendar.getInstance()

        val realMonth = findViewById<TextView>(R.id.realMonth)
        val formMonth = SimpleDateFormat("MMM", Locale.ENGLISH)

        val realDay = findViewById<TextView>(R.id.realDay)
        val formDay = SimpleDateFormat("dd")

        val realYear = findViewById<TextView>(R.id.realYear)
        val formYear = SimpleDateFormat("yyyy")

        val realHour = findViewById<TextView>(R.id.realHour)
        val formHour = SimpleDateFormat("hh")

        val realMinute = findViewById<TextView>(R.id.realMinute)
        val formMinute = SimpleDateFormat("mm")

        val formMeridiem = SimpleDateFormat("aa")
        val realMeridiem = formMeridiem.format(cal.time)

        val realAM = findViewById<ImageView>(R.id.realAM)
        val realPM = findViewById<ImageView>(R.id.realPM)

        if (realMeridiem == "AM") {
            realAM.visibility = View.VISIBLE
            realPM.visibility = View.INVISIBLE
        }

        if (realMeridiem == "PM") {
            realPM.visibility = View.VISIBLE
            realAM.visibility = View.INVISIBLE
        }

        realMonth.text = formMonth.format(cal.time)
        realDay.text = formDay.format(cal.time)
        realYear.text = formYear.format(cal.time)
        realHour.text = formHour.format(cal.time)
        realMinute.text = formMinute.format(cal.time)
    }

}

