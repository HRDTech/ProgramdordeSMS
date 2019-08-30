package com.hrd.programdordesms.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler (context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){
    companion object{
        private val DB_NAME = "dataDB.db"
        private val DB_VERSION = 1
        private val DB_TABLE_NAME = "alarm"
        private val DB_ID = "id"
        private val DB_PHONE = "phone"
        private val DB_MSG = "msg"
        private val DB_TIME = "time"
        private val DB_DATE = "date"
        private val DB_STATUS_ALARM = "status_alarm"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_BD = "CREATE TABLE $DB_TABLE_NAME"+
                "($DB_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$DB_PHONE NUMBER," +
                "$DB_MSG TEXT," +
                "$DB_TIME TEXT," +
                "$DB_DATE TEXT," +
                "$DB_STATUS_ALARM TEXT," +
                "UNIQUE ($DB_ID))"
        db?.execSQL(CREATE_BD)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun add_Alarm_Sms(data: DataClass): Boolean{
        val db = writableDatabase
        val value = ContentValues()
        value.put(DB_PHONE, data.numberPhone)
        value.put(DB_MSG, data.textMsg)
        value.put(DB_TIME, data.textTime)
        value.put(DB_DATE, data.textDate)
        value.put(DB_STATUS_ALARM, data.statusAlm)

        val success = db.insert(DB_TABLE_NAME, null, value)
        return (Integer.parseInt("$success") != -1)
    }

    fun get_Alarm_Sms(): ArrayList<DataClass>{
        var listData: ArrayList<DataClass> = ArrayList()
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $DB_TABLE_NAME"
        val dbCursor = db.rawQuery(selectQuery, null)

        if (dbCursor != null){
            while (dbCursor.moveToNext()){
                var tmpData = DataClass()
                tmpData.idKey = dbCursor.getInt(dbCursor.getColumnIndex(DB_ID))
                tmpData.numberPhone = dbCursor.getInt(dbCursor.getColumnIndex(DB_PHONE))
                tmpData.textMsg = dbCursor.getString(dbCursor.getColumnIndex(DB_MSG))
                tmpData.textTime = dbCursor.getString(dbCursor.getColumnIndex(DB_TIME))
                tmpData.textDate = dbCursor.getString(dbCursor.getColumnIndex(DB_DATE))
                tmpData.statusAlm = dbCursor.getString(dbCursor.getColumnIndex(DB_STATUS_ALARM))
                listData.add(tmpData)
            }
        }

        return listData
    }

    fun update_Alarm_Sms(data: DataClass): Boolean{
        val db = writableDatabase
        val value = ContentValues()
        value.put(DB_PHONE, data.numberPhone)
        value.put(DB_MSG, data.textMsg)
        value.put(DB_TIME, data.textTime)
        value.put(DB_DATE, data.textDate)
        value.put(DB_STATUS_ALARM, data.statusAlm)

        val success = db.update(DB_TABLE_NAME, value, DB_ID +"="+ data.idKey.toString(), null)

        return (Integer.parseInt("$success") != -1)
    }

    fun delete_Alarm_Sms(data: DataClass): Boolean{
        val db = writableDatabase
        val success = db.delete(DB_TABLE_NAME,DB_ID +"="+ data.idKey.toString(), null)

        return (Integer.parseInt("$success") != -1)
    }
}