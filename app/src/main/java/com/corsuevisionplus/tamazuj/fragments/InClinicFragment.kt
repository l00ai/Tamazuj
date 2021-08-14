package com.corsuevisionplus.tamazuj.fragments


import android.R
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.corsuevisionplus.tamazuj.activities.DateAvailableActivity
import com.corsuevisionplus.tamazuj.api.ApiClient
import com.corsuevisionplus.tamazuj.api.ApiInterface
import com.corsuevisionplus.tamazuj.api.response.BayResponse
import com.corsuevisionplus.tamazuj.api.response.OutlineFieldResponse
import com.corsuevisionplus.tamazuj.databinding.FragmentInClinicBinding
import com.corsuevisionplus.tamazuj.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class InClinicFragment : Fragment() {

    private lateinit var binding: FragmentInClinicBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var filedID: String
    private lateinit var doctorID: String
    private lateinit var field: MutableList<FieldOut>
    private lateinit var doctor: MutableList<DataOutline>


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = FragmentInClinicBinding.inflate(inflater, container, false)
        binding = view

        sharedPreferences = activity?.applicationContext!!.getSharedPreferences("SETTING_PREF", AppCompatActivity.MODE_PRIVATE)
        binding.chooseAdviceIN.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               filedID = binding.chooseAdviceIN.selectedItem.toString()

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        fieldsApi(this.activity?.application)
        binding.chooseTheDoctorIN.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                doctorID = binding.chooseTheDoctorIN.selectedItem.toString()

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        doctorsApi(this.activity?.application)



        binding.viewAvailable.setOnClickListener {
            startActivity(Intent(activity, DateAvailableActivity::class.java))
        }

        binding.bookNow.setOnClickListener {
        }

        return binding.root
    }


    private fun fieldsApi(activity: Context?){

        val service: ApiInterface = ApiClient().retrofitInstance!!.create(ApiInterface::class.java)
        val call = service.getFields()
        call.enqueue(object : Callback<OutlineFieldResponse> {
            override fun onResponse(
                    call: Call<OutlineFieldResponse>,
                    response: Response<OutlineFieldResponse>
            ) {

                val res = response.body()!!.data!!
                val f = mutableListOf<List<FieldOut>>()
                for (n in res) {
                    f.add(n.fields!!)
                }

                val arrayData = mutableListOf<String>()
                val arrayField = mutableListOf<FieldOut>()
                for (n in f) {
                    for (x in n) {
                        arrayData.add(x.fieldName!!)
                        arrayField.add(x)
                    }
                }
                field = arrayField

                binding.chooseAdviceIN.adapter = ArrayAdapter(activity!!, R.layout.simple_list_item_1, arrayData)

            }

            override fun onFailure(call: Call<OutlineFieldResponse>, t: Throwable) {
                Log.e("LOI", t.message.toString())
            }

        })
    }
            private fun doctorsApi(activity: Context?){
                val service: ApiInterface = ApiClient().retrofitInstance!!.create(ApiInterface::class.java)
                val call = service.getDoctors()
                call.enqueue(object : Callback<OutlineFieldResponse> {
                    override fun onResponse(
                            call: Call<OutlineFieldResponse>,
                            response: Response<OutlineFieldResponse>
                    ) {
                        val res = response.body()!!.data!!
                        val arrayData = mutableListOf<String>()
                        val arrayDoctor = mutableListOf<DataOutline>()
                        for (n in res) {
                            arrayData.add(n.doctorName!!)
                            arrayDoctor.add(n)
                        }
                        doctor = arrayDoctor
                        binding.chooseTheDoctorIN.adapter = ArrayAdapter(
                                activity!!,
                                R.layout.simple_list_item_1,
                                arrayData
                        )
                    }

                    override fun onFailure(call: Call<OutlineFieldResponse>, t: Throwable) {
                        Log.e("LOI", t.message.toString())
                    }
                })

}
    private fun outlineBayApi() {
        var fieldId:Int? = null
        for (item in field){
            if (filedID.equals(item.fieldName)){
                fieldId = item.fieldId!!
                return
            }
        }
        var doctorId:Int? = null
        for (item in doctor){
            if (doctorID.equals(item.doctorName)){
                doctorId = item.doctorId!!
                return
            }
        }
        val timeFrom = sharedPreferences.getString("DATEFROM", null)
        val timeTo = sharedPreferences.getString("DATETO", null)
        val note = binding.notes.text.toString()
        val typeBay = "paypal"
        val service: ApiInterface = ApiClient().retrofitInstance!!.create(ApiInterface::class.java)
        Log.d("Data_Online", "${fieldId}${doctorId}${note}${typeBay}")
        val call = service.roomOutline(doctorId.toString(), fieldId.toString(), note, typeBay, timeFrom!!, timeTo!!)
        call.enqueue(object : Callback<BayResponse> {
            override fun onResponse(call: Call<BayResponse>, response: Response<BayResponse>) {
                if (response.code() == 200) {
                    val body = response.body()
                    if (body!!.states!!) {
                        val url = body.bayLink!!
                        val webView = object : WebViewClient() {
                            override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                                super.doUpdateVisitedHistory(view, url, isReload)
                            }
                        }
                        webView.doUpdateVisitedHistory(view as WebView?, url, true)
                        Log.d("UrlPay", url)
                        val webViewClient = object : WebViewClient() {
                            override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
                                return super.shouldInterceptRequest(view, url)
                            }
                        }
                    }

                } else (response.code() == 422)
                Toast.makeText(
                        activity,
                        "Error",
                        Toast.LENGTH_LONG
                )
                        .show()
            }

            override fun onFailure(call: Call<BayResponse>, t: Throwable) {
                Toast.makeText(activity, t.localizedMessage, Toast.LENGTH_LONG)
                        .show()
            }


        })

    }

}



