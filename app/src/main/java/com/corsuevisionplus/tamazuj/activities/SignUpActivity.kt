package com.corsuevisionplus.tamazuj.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.corsuevisionplus.tamazuj.api.ApiClient
import com.corsuevisionplus.tamazuj.api.ApiInterface
import com.corsuevisionplus.tamazuj.api.response.CommonResponse
import com.corsuevisionplus.tamazuj.databinding.ActivitySignUpBinding
import com.corsuevisionplus.tamazuj.models.Tokens
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpActivity : AppCompatActivity() {
    private lateinit var signUpBinding: ActivitySignUpBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = signUpBinding.root
        setContentView(view)
        sharedPreferences = getSharedPreferences("SETTING_PREF", MODE_PRIVATE)
        readData()
        signUpBinding.signIn.setOnClickListener {
            registrationApi()

        }
    }
    private fun registrationApi() {
        val name = signUpBinding.nameSign.text.toString()
        val email = signUpBinding.emailSign.text.toString()
        val phone = signUpBinding.mobileNum.text.toString()
        val language = signUpBinding.lanSign.text.toString()
        val dateOfBerth = signUpBinding.age.text.toString()
        val gender = signUpBinding.gender.text.toString()
        val password = signUpBinding.passwordSing.text.toString()
        val cpassword = signUpBinding.passwordSing.text.toString()
        val fcm = "fcm"

        val service: ApiInterface = ApiClient().retrofitInstance!!.create(ApiInterface::class.java)
     Log.d(
         "User_Data",
         "${name}${email}${phone}${language}${dateOfBerth}${gender}${password}${cpassword}${fcm}"
     )
        val call = service.register(
            name,
            email,
            phone,
            language,
            gender,
            dateOfBerth,
            fcm,
            password,
            cpassword
        )
        call.enqueue(object : Callback<CommonResponse> {
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {

                if (response.code() == 201) {
                    val body = response.body()
                    val gson = Gson()
                    val userObject = gson.toJson(body!!.userInfo)
                    if (body.status!!) {
                        finish()
                        val intent = Intent(this@SignUpActivity, TamazujTeamActivity::class.java)
                        startActivity(intent)
                        val edit = sharedPreferences.edit()
                        edit.putString("ID",body.userInfo!!.id.toString())
                        edit.putString("EMAIL", signUpBinding.emailSign.text.toString())
                        edit.putString("NAME", signUpBinding.nameSign.text.toString())
                        edit.putString("PASSWORD", signUpBinding.passwordSing.text.toString())
                        edit.putString("GENDER", signUpBinding.gender.text.toString())
                        edit.putString("LANG", signUpBinding.lanSign.text.toString())
                        edit.putString("PHONE", signUpBinding.mobileNum.text.toString())
                        edit.putString("DATEOFBERTH", signUpBinding.age.text.toString())
                        edit.putString("FCM", body.tokens!!.accessToken)
                        edit.apply()


                    } else {
                        Toast.makeText(this@SignUpActivity, userObject, Toast.LENGTH_LONG)
                            .show()
                    }
                } else if (response.code() == 422) {
                    Toast.makeText(
                        this@SignUpActivity,
                        "قيمة الحقل البريد الالكتروني مُستخدمة من قبل",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }

            override fun onFailure(call: Call<CommonResponse>?, t: Throwable?) {
                Toast.makeText(this@SignUpActivity, t!!.localizedMessage, Toast.LENGTH_LONG)
                    .show()
            }
        })

    }
    private fun readData(){
        signUpBinding.emailSign.setText(sharedPreferences.getString("EMAIL", null))
        signUpBinding.passwordSing.setText(sharedPreferences.getString("PASSWORD", null))
        signUpBinding.nameSign.setText(sharedPreferences.getString("NAME", null))
        signUpBinding.gender.setText(sharedPreferences.getString("GENDER", null))
        signUpBinding.age.setText(sharedPreferences.getString("DATEOFBERTH", null))
        signUpBinding.lanSign.setText(sharedPreferences.getString("LANG", null))
        signUpBinding.mobileNum.setText(sharedPreferences.getString("PHONE", null))
        sharedPreferences.getString("FCM",null)
    }
}