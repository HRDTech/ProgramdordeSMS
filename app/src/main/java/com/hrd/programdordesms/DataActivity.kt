package com.hrd.programdordesms

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.hrd.programdordesms.model.DataClass
import com.hrd.programdordesms.model.DatabaseHandler
import java.util.*

class DataActivity : AppCompatActivity() {
    private var textTime: TextView? = null
    private var textDate: TextView? = null
    private var textSMS: TextView? = null
    private var textNumberPhone: TextView? = null
    private var handlerDB: DatabaseHandler? = null
    private val dataAlarm: DataClass = DataClass()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        textTime = findViewById(R.id.textTime)
        textDate = findViewById(R.id.textDate)
        textSMS = findViewById(R.id.textSMS)
        textNumberPhone = findViewById(R.id.textPhone)

        handlerDB = DatabaseHandler(this)

    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId){
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    fun onClick_Time(view: View){
        val handler = Calendar.getInstance()
        val hour = handler.get(Calendar.HOUR)
        val minute = handler.get(Calendar.MINUTE)

        val dialogTime = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener(function = {view, h, m ->
            textTime!!.setText(h.toString() + ":" + m.toString())
        }), hour, minute, false)
        dialogTime.show()
    }

    fun onClick_Date(view: View){
        val handler = Calendar.getInstance()
        val day = handler.get(Calendar.DAY_OF_MONTH)
        val month = handler.get(Calendar.MONTH)
        val year = handler.get(Calendar.YEAR)

        val dialogDate = DatePickerDialog(this, DatePickerDialog.OnDateSetListener(function = {
            view, year, month, dayOfMonth ->
            textDate!!.setText(dayOfMonth.toString() + "/" + month.toString() + "/" + year.toString())
        }), year, month, day)
        dialogDate.show()
    }

    fun onClick_InsertData(view: View){
        dataAlarm.numberPhone = Integer.parseInt(textNumberPhone!!.text.toString())
        dataAlarm.textMsg = textSMS!!.text.toString()
        dataAlarm.textTime = textTime!!.text.toString()
        dataAlarm.textDate = textDate!!.text.toString()
        dataAlarm.statusAlm = "activated"

        if (handlerDB!!.add_Alarm_Sms(dataAlarm)){
            Clear_Text()
        }
    }

    fun onClick_CancelData(view: View){
        Clear_Text()
        onBackPressed()
    }

    private fun Clear_Text(){
        textNumberPhone!!.text = ""
        textSMS!!.text = ""
        textDate!!.text = ""
        textTime!!.text = ""

    }
}
