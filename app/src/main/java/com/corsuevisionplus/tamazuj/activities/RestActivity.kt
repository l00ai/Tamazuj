package com.corsuevisionplus.tamazuj.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.corsuevisionplus.tamazuj.R
import com.corsuevisionplus.tamazuj.api.ApiClient
import com.corsuevisionplus.tamazuj.api.ApiInterface
import com.corsuevisionplus.tamazuj.api.response.CommonResponse
import com.corsuevisionplus.tamazuj.api.response.RestResponse
import com.corsuevisionplus.tamazuj.databinding.ActivityRestBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.annotation.Generated


class RestActivity : AppCompatActivity() {
    private lateinit var restBinding: ActivityRestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        restBinding = ActivityRestBinding.inflate(layoutInflater)
        val view = restBinding.root
        setContentView(view)

        restBinding.restPass.setOnClickListener {
            restApi()
        }
    }
    private fun restApi(){
        val email = restBinding.emailR.text.toString()
        val service: ApiInterface = ApiClient().retrofitInstance!!.create(ApiInterface::class.java)
        val call = service.rest(email)
        call.enqueue(object : Callback<RestResponse> {
            override fun onResponse(
                call: Call<RestResponse>,
                response: Response<RestResponse>
            ) {
                if (response.code() == 200) {
                    val body = response.body()
                    if (body!!.status!!) {
                       
                        Toast.makeText(this@RestActivity,body.message, Toast.LENGTH_LONG)
                            .show()
                        finish()
                        val intent = Intent(this@RestActivity, LogInActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this@RestActivity,body.message, Toast.LENGTH_LONG)
                            .show()
                    }
                } else if (response.code() == 400) {
                    Toast.makeText(this@RestActivity,"Error", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<RestResponse>?, t: Throwable?) {

                Toast.makeText(this@RestActivity, t!!.localizedMessage, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }


}
