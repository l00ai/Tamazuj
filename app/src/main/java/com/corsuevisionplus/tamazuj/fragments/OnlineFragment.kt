package com.corsuevisionplus.tamazuj.fragments


import android.R
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.corsuevisionplus.tamazuj.api.ApiClient
import com.corsuevisionplus.tamazuj.api.ApiInterface
import com.corsuevisionplus.tamazuj.api.response.BayResponse
import com.corsuevisionplus.tamazuj.api.response.OnlineFieldResponse
import com.corsuevisionplus.tamazuj.databinding.FragmentOnlineBinding
import com.corsuevisionplus.tamazuj.models.DataOnline
import com.corsuevisionplus.tamazuj.models.Field
import okhttp3.HttpUrl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OnlineFragment : Fragment() {
    private lateinit var binding: FragmentOnlineBinding
    private lateinit var field: MutableList<Field>
    private lateinit var doctor: MutableList<DataOnline>
    private lateinit var filedID: String
    private lateinit var doctorID: String
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = FragmentOnlineBinding.inflate(inflater, container, false)
        binding = view


        binding.chooseAdvice.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
              filedID = binding.chooseAdvice.selectedItem.toString()

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        fieldsApiOn(this.activity?.application)

        binding.chooseTheDoctor.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               doctorID = binding.chooseTheDoctor.selectedItem.toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        doctorsApiOn(this.activity?.application)



        binding.timerSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               binding.timerSpinner.selectedItem.toString()
            }

        }
        binding.ChooseCommunication.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                binding.ChooseCommunication.selectedItem.toString()
            }

        }
        binding.chooseInterview.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                binding.chooseInterview.selectedItem.toString()
            }


        }

            binding.request.setOnClickListener {
                onlineBayApi()
            }

        return binding.root
    }
    private fun fieldsApiOn(activity: Context?){

        val service: ApiInterface = ApiClient().retrofitInstance!!.create(ApiInterface::class.java)
        val call = service.getFieldsOnline()
        call.enqueue(object : Callback<OnlineFieldResponse> {
            override fun onResponse(
                    call: Call<OnlineFieldResponse>,
                    response: Response<OnlineFieldResponse>
            ) {

                val res = response.body()!!.data!!
                val f = mutableListOf<List<Field>>()

                for (n in res) {
                    f.add(n.fields!!)

                }
                val arrayData = mutableListOf<String>()
                val arrayField = mutableListOf<Field>()
                for (n in f) {
                    for (x in n) {
                        arrayData.add(x.fieldName!!)
                        arrayField.add(x)
                    }

                }
                field = arrayField

                binding.chooseAdvice.adapter = ArrayAdapter(activity!!, R.layout.simple_list_item_1, arrayData)


            }

            override fun onFailure(call: Call<OnlineFieldResponse>, t: Throwable) {
                Log.e("LOI", t.message.toString())
            }

        })
    }

    private fun doctorsApiOn(activity: Context?){
         val service: ApiInterface = ApiClient().retrofitInstance!!.create(ApiInterface::class.java)
         val call = service.getDoctorsOnline()
         call.enqueue(object : Callback<OnlineFieldResponse> {
             override fun onResponse(
                     call: Call<OnlineFieldResponse>,
                     response: Response<OnlineFieldResponse>
             ) {
                 val res = response.body()!!.data!!
                 val arrayData = mutableListOf<String>()
                 val arrayDoctor = mutableListOf<DataOnline>()
                 for (n in res) {
                   arrayData.add(n.doctorName!!)
                    arrayDoctor.add(n)
                 }
                    doctor = arrayDoctor
                 binding.chooseTheDoctor.adapter = ArrayAdapter(
                         activity!!,
                         R.layout.simple_list_item_1,
                         arrayData
                 )


             }

             override fun onFailure(call: Call<OnlineFieldResponse>, t: Throwable) {
                 Log.e("LOI", t.message.toString())
             }
         })

     }
    private fun onlineBayApi() {

        var fieldId:Int? = null
        for (item in field){
            if (filedID.equals(item.fieldName)){
                fieldId = item.fieldId!!
                break
            }
        }

        var doctorId:Int? = null
        for (item in doctor){
            if (doctorID.equals(item.doctorName)){
                doctorId = item.doctorId!!
                break
            }
        }

        val note = binding.noteOnline.text.toString()
        val typeBay = "paypal"
        val timer = binding.timerSpinner.selectedItem.toString()


        val service: ApiInterface = ApiClient().retrofitInstance!!.create(ApiInterface::class.java)
            Log.d("Data_Online", "${fieldId}${doctorId}${note}${typeBay}${timer}")
            val call = service.roomOnline(doctorId.toString(), fieldId.toString(), note, typeBay, timer)
            call.enqueue(object : Callback<BayResponse> {
                override fun onResponse(call: Call<BayResponse>, response: Response<BayResponse>) {
                    Log.d("LOI", response.toString());

                    if (response.code() == 200) {
                        val body = response.body()
                        Log.d("Data_Online", body.toString())

                        if (body!!.states!!) {
                            val url = body.bayLink!!
                            val webView = object : WebViewClient() {
                                override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                                    super.doUpdateVisitedHistory(view, url, isReload)
                                }
                            }
                            webView.doUpdateVisitedHistory(view as WebView?, url, true)
                            Log.d("UrlPay", url)
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




