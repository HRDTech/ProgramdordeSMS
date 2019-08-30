package com.hrd.programdordesms.model

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.hrd.programdordesms.R

class DataAdapter(private val tmpListData: ArrayList<DataClass>):RecyclerView.Adapter<DataAdapter.DataHolderView>() {

    lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DataHolderView {
        setOnItemClickListener(listener)
        val theView = LayoutInflater.from(p0.context).inflate(R.layout.layout_item_alarm, p0, false)
        return DataHolderView(theView)
    }

    override fun getItemCount(): Int {
        return tmpListData.size
    }

    override fun onBindViewHolder(p0: DataHolderView, p1: Int) {
        val data = tmpListData[p1]
        p0.textPhone.text = tmpListData[p1].numberPhone.toString()
        p0.textTime.text = tmpListData[p1].textTime
        p0.textDate.text = tmpListData[p1].textDate
        p0.textStatus.text = tmpListData[p1].statusAlm
        p0.itemView.setOnClickListener ({
            listener.onClick(it, data)
        })
    }

    inner class DataHolderView(itemView: View): RecyclerView.ViewHolder(itemView){
        internal var textPhone: TextView
        internal var textTime: TextView
        internal var textDate: TextView
        internal var textStatus: TextView

        init {
            textPhone = itemView.findViewById(R.id.textDisplayPhone) as TextView
            textTime = itemView.findViewById(R.id.textDisplayTime) as TextView
            textDate = itemView.findViewById(R.id.textDisplayDate) as TextView
            textStatus = itemView.findViewById(R.id.textDisplayStatus) as TextView
        }
    }

    interface OnItemClickListener {
        fun onClick(view: View, data: DataClass)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}