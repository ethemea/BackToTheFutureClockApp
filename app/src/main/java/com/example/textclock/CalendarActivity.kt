package com.example.textclock

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.time.format.DateTimeFormatter
import java.util.*


class CalendarActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar_layout)

        val mCalendarView = findViewById<CalendarView>(R.id.calendarView)

        mCalendarView.setOnDateChangeListener { _, i, i1, i2 ->
            val yyyy = "$i"

            val M_ = i1 + 1
            var MM = ""
            if (M_ < 10) {
                MM = "0$M_"
            } else {
                MM = "$M_"
            }
            val formatter = DateTimeFormatter.ofPattern("MM")
            val date = formatter.parse(MM)
            MM = DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH).format(date)

            var dd = ""
            if (i2 < 10) {
                dd = "0$i2"
            } else {
                dd = "$i2"
            }
            val str = intent.extras?.getString("typeTime")

            val intent = Intent(this@CalendarActivity, MainActivity::class.java)
            intent.putExtra("year", yyyy)
            intent.putExtra("month", MM)
            intent.putExtra("day", dd)

            if (str == "future") {
                intent.putExtra("tt", "future")
            }

            val picker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(10)
                    .setMinute(10)
                    .build()
            picker.show(supportFragmentManager, "tag");
            var hh = ""
            var mm = ""
            var aa = ""
            picker.addOnPositiveButtonClickListener {
                when (picker.hour) {
                    0 -> {
                        hh = "12"
                        aa = "AM"
                    }
                    in 1..9 -> {
                        hh = "0" + picker.hour.toString()
                        aa = "AM"
                    }
                    in 10..12 -> {
                        hh = picker.hour.toString()
                        aa = if (picker.hour == 12) "PM"
                        else "AM"
                    }
                    in 13..23 -> {
                        aa = "PM"
                        val h = picker.hour - 12
                        hh = if (h in 1..9) {
                            "0$h"
                        } else {
                            h.toString()
                        }
                    }
                }


                if (picker.minute < 10)
                mm = "0" + picker.minute.toString()
                else  mm = picker.minute.toString()

                intent.putExtra("hour", hh)
                intent.putExtra("minute", mm)
                intent.putExtra("meridien", aa)

                startActivity(intent)
            }
        }
    }

}