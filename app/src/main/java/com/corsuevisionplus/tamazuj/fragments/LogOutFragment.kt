package com.corsuevisionplus.tamazuj.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.corsuevisionplus.tamazuj.activities.LogInActivity
import com.corsuevisionplus.tamazuj.api.ApiClient
import com.corsuevisionplus.tamazuj.api.ApiInterface
import com.corsuevisionplus.tamazuj.api.response.LogOutResponse
import com.corsuevisionplus.tamazuj.databinding.FragmentLogOutBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LogOutFragment : Fragment() {
    private lateinit var binding: FragmentLogOutBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = FragmentLogOutBinding.inflate(inflater, container, false)
        binding = view
        sharedPreferences = activity?.applicationContext!!.getSharedPreferences("SETTING_PREF", AppCompatActivity.MODE_PRIVATE)
        binding.logOutBtn.setOnClickListener {
            logOutApi()
        }


        return binding.root
    }


    private fun logOutApi() {
        val service: ApiInterface = ApiClient().retrofitInstance!!.create(ApiInterface::class.java)
        val call = service.logOutApp()
        call.enqueue(object : Callback<LogOutResponse> {
            override fun onResponse(
                    call: Call<LogOutResponse>,
                    response: Response<LogOutResponse>
            ) {
                if (response.code() == 200) {
                    val body = response.body()
                    if (body!!.status!!) {

                        Toast.makeText(activity, body.message, Toast.LENGTH_LONG).show();

                        val edit = sharedPreferences.edit()
                        edit.clear()
                        edit.apply()

                        val intent = Intent(activity, LogInActivity::class.java)
                        startActivity(intent)

                        activity?.finish()


                    } else {
                        Toast.makeText(activity, body.message, Toast.LENGTH_LONG)
                                .show()
                    }
                } else if (response.code() == 400) {
                    Toast.makeText(activity, "Error", Toast.LENGTH_LONG)
                            .show()
                }
            }

            override fun onFailure(call: Call<LogOutResponse>?, t: Throwable?) {

                Toast.makeText(activity, t!!.localizedMessage, Toast.LENGTH_LONG)
                        .show()
            }
        })
    }

}

