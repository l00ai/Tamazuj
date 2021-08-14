package com.corsuevisionplus.tamazuj.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.corsuevisionplus.tamazuj.activities.TamazujTeamActivity
import com.corsuevisionplus.tamazuj.api.ApiClient
import com.corsuevisionplus.tamazuj.api.ApiInterface
import com.corsuevisionplus.tamazuj.api.response.CommonResponse
import com.corsuevisionplus.tamazuj.api.response.SettingsResponse
import com.corsuevisionplus.tamazuj.databinding.FragmentSettingsBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view = FragmentSettingsBinding.inflate(inflater, container, false)
        binding = view
        sharedPreferences = activity?.applicationContext!!.getSharedPreferences("SETTING_PREF", AppCompatActivity.MODE_PRIVATE)
        readData()
        binding.editProfile.setOnClickListener {
            upDateApi()
        }

        return binding.root
    }
    private fun upDateApi() {
        val name = binding.nameSet.text.toString()
        val email = binding.emailSet.text.toString()
        val phone = binding.mobileNumSet.text.toString()
        val language = binding.lanSet.text.toString()
        val dateOfBerth = binding.ageSet.text.toString()
        val gender = binding.genderSet.text.toString()
        val password = binding.passwordSet.text.toString()


        val service: ApiInterface = ApiClient().retrofitInstance!!.create(ApiInterface::class.java)
        Log.d(
                "User_UpDate",
                "${name}${email}${phone}${language}${dateOfBerth}${gender}${password}"
        )
        val call = service.upDate(name,email,phone,language,gender,dateOfBerth,password)
        call.enqueue(object : Callback<SettingsResponse> {
            override fun onResponse(
                    call: Call<SettingsResponse>,
                    response: Response<SettingsResponse>
            ) {

                if (response.code() == 201) {
                    val body = response.body()
                    val gson = Gson()
                    val userObject = gson.toJson(body!!.userInfo)
                    if (body.status!!) {
                        Toast.makeText(activity, body.message, Toast.LENGTH_LONG)
                                .show()
                        activity!!.finish()
                        val intent = Intent(activity, ProfileFragment::class.java)
                        startActivity(intent)
                        val edit = sharedPreferences.edit()
                        edit.putString("EMAIL", binding.emailSet.text.toString())
                        edit.putString("NAME", binding.nameSet.text.toString())
                        edit.putString("PASSWORD", binding.passwordSet.text.toString())
                        edit.putString("GENDER", binding.genderSet.text.toString())
                        edit.putString("LANG", binding.lanSet.text.toString())
                        edit.putString("PHONE", binding.mobileNumSet.text.toString())
                        edit.putString("DATEOFBERTH", binding.ageSet.text.toString())
                        edit.apply()


                    } else {
                        Toast.makeText(activity, userObject, Toast.LENGTH_LONG)
                                .show()
                    }
                } else if (response.code() == 401) {
                    Toast.makeText(activity,response.body()!!.message ,Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<SettingsResponse>?, t: Throwable?) {
                Toast.makeText(activity, t!!.localizedMessage, Toast.LENGTH_LONG)
                        .show()
            }
        })

    }
    private fun readData(){
        binding.emailSet.setText(sharedPreferences.getString("EMAIL", ""))
        binding.passwordSet.setText(sharedPreferences.getString("PASSWORD", ""))
        binding.nameSet.setText(sharedPreferences.getString("NAME", ""))
        binding.genderSet.setText(sharedPreferences.getString("GENDER", ""))
        binding.ageSet.setText(sharedPreferences.getString("DATEOFBERTH", ""))
        binding.lanSet.setText(sharedPreferences.getString("LANG", ""))
        binding.mobileNumSet.setText(sharedPreferences.getString("PHONE", ""))
    }
        }
