package com.corsuevisionplus.tamazuj.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.corsuevisionplus.tamazuj.R
import com.corsuevisionplus.tamazuj.databinding.ActivityConsultationsBinding
import com.corsuevisionplus.tamazuj.fragments.InClinicFragment
import com.corsuevisionplus.tamazuj.fragments.OnlineFragment
import javax.annotation.Generated


class ConsultationsActivity : AppCompatActivity() {
    private lateinit var consultationsBinding: ActivityConsultationsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        consultationsBinding = ActivityConsultationsBinding.inflate(layoutInflater)
        val view = consultationsBinding.root
        setContentView(view)
        consultationsBinding.menuDrawer.setOnClickListener {
            val intent = Intent(this,MainDrawerActivity::class.java)
            startActivity(intent)
        }

        consultationsBinding.bottomNaf.setOnItemSelectedListener {
            if (it.itemId == R.id.online){
                switchFragment(OnlineFragment())
                return@setOnItemSelectedListener true
            }else{
                switchFragment(InClinicFragment())
                return@setOnItemSelectedListener true
            }
        }

    }
    private fun switchFragment(fragment: Fragment){
        val fm = supportFragmentManager
        fm.beginTransaction().replace(R.id.container,fragment).commit()

    }

}