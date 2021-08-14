package com.corsuevisionplus.tamazuj.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.corsuevisionplus.tamazuj.adapters.RestAdapter
import com.corsuevisionplus.tamazuj.api.ApiClient
import com.corsuevisionplus.tamazuj.api.ApiInterface
import com.corsuevisionplus.tamazuj.api.response.TimeResponse
import com.corsuevisionplus.tamazuj.databinding.ActivityDateAvailableBinding
import com.corsuevisionplus.tamazuj.fragments.InClinicFragment
import com.corsuevisionplus.tamazuj.models.DataTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class DateAvailableActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var binding: ActivityDateAvailableBinding
    private lateinit var adapter: RestAdapter
    private lateinit var dateNew: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDateAvailableBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.datePicker.setOnDateChangeListener { calendarView: CalendarView, year: Int, month: Int, day: Int ->
            val m = month + 1
            dateNew = "$year-$m-$day"
            timeApi(dateNew)
        }

       val lm = LinearLayoutManager(this)
        binding.recyclerTimes.layoutManager = lm

        binding.recyclerTimes.setOnClickListener {
            startActivity(Intent(this,InClinicFragment::class.java))
        }
    }

    private fun checkEmptyStatus(list: List<DataTime>) {
        if (list.isEmpty()) {
            binding.recyclerTimes.visibility = View.GONE
        } else {
            binding.recyclerTimes.visibility = View.VISIBLE
        }
    }


    private fun timeApi(date: String) {

        val service: ApiInterface = ApiClient().retrofitInstance!!.create(ApiInterface::class.java)
        val call = service.availableTime(date)
        call.enqueue(object : Callback<TimeResponse> {

            override fun onResponse(
                call: Call<TimeResponse>,
                response: Response<TimeResponse>
            ) {
                binding.swipeToRefreash.isRefreshing = false
                if (response.code() == 200) {
                    val body = response.body()
                    Log.e("LOI", body?.dataTime?.get(0)!!.timeStart!!)
                    if (body.status!!) {
                        if(response.body()?.dataTime != null){
                            adapter = RestAdapter(this@DateAvailableActivity, response.body()?.dataTime)
                            binding.recyclerTimes.adapter = adapter
                            checkEmptyStatus(response.body()!!.dataTime!!)
                        }else{
                            // toast
                        }
                    }
                } else if (response.code() == 400) {
                    Toast.makeText(this@DateAvailableActivity, "Error", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<TimeResponse>, t: Throwable) {
                Toast.makeText(this@DateAvailableActivity, t.localizedMessage, Toast.LENGTH_LONG)
                    .show()
            }

        })

    }

    override fun onRefresh() {
        timeApi("")
    }
}
