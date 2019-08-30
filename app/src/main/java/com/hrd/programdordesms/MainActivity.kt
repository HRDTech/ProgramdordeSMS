package com.hrd.programdordesms

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.hrd.programdordesms.model.DataAdapter
import com.hrd.programdordesms.model.DataClass
import com.hrd.programdordesms.model.DatabaseHandler

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var handlerDB: DatabaseHandler? = null
    private var listData: ArrayList<DataClass> = ArrayList()
    private lateinit var recyclerViewList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        handlerDB = DatabaseHandler(this)

        recyclerViewList = findViewById(R.id.listData)

        fab.setOnClickListener { view ->
            startActivity(Intent(this, DataActivity::class.java))
        }

        setDisplay_Data()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setDisplay_Data(){
        listData = handlerDB!!.get_Alarm_Sms()

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewList.layoutManager = layoutManager
        val tmpAdapter = DataAdapter(listData)
        tmpAdapter.setOnItemClickListener(object : DataAdapter.OnItemClickListener{
            override fun onClick(view: View, data: DataClass) {
                Toast.makeText(applicationContext, data.idKey.toString(), Toast.LENGTH_LONG).show()
            }
        })
        recyclerViewList.adapter = tmpAdapter
    }

    override fun onPostResume() {
        super.onPostResume()
        setDisplay_Data()
    }

}
