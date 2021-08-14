package com.corsuevisionplus.tamazuj.adapters

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.corsuevisionplus.tamazuj.R
import com.corsuevisionplus.tamazuj.activities.ConsultationsActivity
import com.corsuevisionplus.tamazuj.api.ApiClient
import com.corsuevisionplus.tamazuj.api.ApiInterface
import com.corsuevisionplus.tamazuj.api.response.TimeResponse
import com.corsuevisionplus.tamazuj.fragments.InClinicFragment
import com.corsuevisionplus.tamazuj.interfaces.OnItemClick
import com.corsuevisionplus.tamazuj.models.DataTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RestAdapter(private val context: Context, arrayList: List<DataTime>?) :
        RecyclerView.Adapter<RestAdapter.ExpensesViewHolder>() {
    private val onItemClick : OnItemClick? = null
    private val arrayList: List<DataTime>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesViewHolder {
        val v: View = LayoutInflater.from(context).inflate(R.layout.rv_times_cell, parent, false)
        return ExpensesViewHolder(v)
    }



    override fun onBindViewHolder(holder: ExpensesViewHolder, position: Int) {
        holder.openType.text = arrayList[position].openType.toString()
        holder.timeStart.text = arrayList[position].timeStart
        holder.timeEnd.text = arrayList[position].timeEnd
        holder.linerItem.setOnClickListener {
            onItemClick!!.itemSelected(it, position)
            if(arrayList[position].openType == true){
                val dateF = arrayList[position].dateFrom
               val dateT =  arrayList[position].dateTo
                lateinit var sharedPreferences: SharedPreferences
                sharedPreferences = context.getSharedPreferences("SETTING_PREF", AppCompatActivity.MODE_PRIVATE)
                val edit = sharedPreferences.edit()
                edit.putString("DATEFROM", dateF)
                edit.putString("DATETO", dateT)
                edit.apply()

            }else{
                // toast
            }
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ExpensesViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
        var openType: TextView
        var timeStart: TextView
        var timeEnd: TextView
        var linerItem: LinearLayout


        init {
            openType = itemView.findViewById(R.id.typeOpen)
            timeStart = itemView.findViewById(R.id.timeStart)
            timeEnd = itemView.findViewById(R.id.timeEnd)
            linerItem = itemView.findViewById(R.id.linerItem)

        }
    }

    init {
        this.arrayList = arrayList!!
    }


}