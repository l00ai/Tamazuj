package com.corsuevisionplus.tamazuj.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Spinner
import com.corsuevisionplus.tamazuj.R
import com.corsuevisionplus.tamazuj.databinding.ActivityMainBinding
import com.corsuevisionplus.tamazuj.models.UserInfo
import javax.annotation.Generated


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        animation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                val intent = Intent(this@MainActivity, LogInActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })
        binding.photoAny.startAnimation(animation)
    }}
