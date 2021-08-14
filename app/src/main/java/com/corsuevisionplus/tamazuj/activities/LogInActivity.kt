package com.corsuevisionplus.tamazuj.activities

import android.app.ActionBar
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.corsuevisionplus.tamazuj.api.ApiClient
import com.corsuevisionplus.tamazuj.api.ApiInterface
import com.corsuevisionplus.tamazuj.api.response.CommonResponse
import com.corsuevisionplus.tamazuj.databinding.ActivityLogInBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LogInActivity : AppCompatActivity() {
    private lateinit var logInBinding: ActivityLogInBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logInBinding = ActivityLogInBinding.inflate(layoutInflater)
        val view = logInBinding.root
        setContentView(view)
        sharedPreferences = getSharedPreferences("SETTING_PREF", MODE_PRIVATE)
        readData()
      val emailLog = logInBinding.emailLogin.text.toString()
        val passLog = logInBinding.passwordLogin.text.toString()
        if ((passLog.isEmpty() && emailLog.isEmpty())){
            logInBinding.LogIn.setOnClickListener {
                loginApi()
            }
        }
        else{
            val intent = Intent(this@LogInActivity, ConsultationsActivity::class.java)
            startActivity(intent)
            finish()
        }

        logInBinding.signUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        logInBinding.forgotPassword.setOnClickListener {
            val intent = Intent(this, RestActivity::class.java)
            startActivity(intent)
        }
    }


    private fun readData(){
        logInBinding.emailLogin.setText(sharedPreferences.getString("EMAIL", null))
        logInBinding.passwordLogin.setText(sharedPreferences.getString("PASSWORD", null))
    }
    private fun loginApi(){
        val email =logInBinding.emailLogin.text.toString()
        val password = logInBinding.passwordLogin.text.toString()
        val fcm = "fcm"
        val service: ApiInterface = ApiClient().retrofitInstance!!.create(ApiInterface::class.java)
        val call = service.logIn(email, password, fcm)
        call.enqueue(object : Callback<CommonResponse> {
            override fun onResponse(
                    call: Call<CommonResponse>,
                    response: Response<CommonResponse>
            ) {
                if (response.code() == 200) {
                    val body = response.body()
                    val gson = Gson()
                    val userObject = gson.toJson(body!!.userInfo)
                    if (body.status!!) {
                        finish()
                        val intent = Intent(this@LogInActivity, ConsultationsActivity::class.java)
                        startActivity(intent)
                        val edit = sharedPreferences.edit()
                        edit.putString("EMAIL", logInBinding.emailLogin.text.toString())
                        edit.putString("PASSWORD", logInBinding.passwordLogin.text.toString())
                        edit.putString("ID",body.userInfo!!.id.toString())
                        edit.putString("NAME", body.userInfo!!.name)
                        edit.putString("GENDER", body.userInfo!!.gender)
                        edit.putString("LANG", body.userInfo!!.lang)
                        edit.putString("PHONE", body.userInfo!!.phone)
                        edit.putString("DATEOFBERTH", body.userInfo!!.dateOfBerth)
                        edit.putString("FCM", body.tokens!!.accessToken)
                        edit.apply()


                    } else {
                        Toast.makeText(this@LogInActivity, userObject, Toast.LENGTH_LONG)
                                .show()
                    }
                } else if (response.code() == 401) {
                    Toast.makeText(this@LogInActivity, "Error", Toast.LENGTH_LONG)
                            .show()
                }
            }

            override fun onFailure(call: Call<CommonResponse>?, t: Throwable?) {

                Toast.makeText(this@LogInActivity, t!!.localizedMessage, Toast.LENGTH_LONG)
                        .show()
            }
        })
    }


}
