package com.corsuevisionplus.tamazuj.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.corsuevisionplus.tamazuj.R
import com.corsuevisionplus.tamazuj.databinding.ActivityTamazujTeamBinding
import javax.annotation.Generated


class TamazujTeamActivity : AppCompatActivity() {
    private lateinit var tamazujTeamBinding: ActivityTamazujTeamBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tamazujTeamBinding = ActivityTamazujTeamBinding.inflate(layoutInflater)
        val view = tamazujTeamBinding.root
        setContentView(view)
        tamazujTeamBinding.skipBtn.setOnClickListener {
            val intent = Intent(this, ConsultationsActivity::class.java)
            startActivity(intent)
        }
    }
    }