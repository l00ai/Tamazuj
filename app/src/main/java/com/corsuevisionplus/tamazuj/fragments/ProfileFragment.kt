package com.corsuevisionplus.tamazuj.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.corsuevisionplus.tamazuj.R
import com.corsuevisionplus.tamazuj.api.ApiClient
import com.corsuevisionplus.tamazuj.api.ApiInterface
import com.corsuevisionplus.tamazuj.databinding.FragmentProfileBinding
import com.corsuevisionplus.tamazuj.models.UserInfo



class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?)
            : View? {
        // Inflate the layout for this fragment
        val view = FragmentProfileBinding.inflate(inflater, container, false)
        binding = view
        sharedPreferences = activity?.applicationContext!!.getSharedPreferences("SETTING_PREF", AppCompatActivity.MODE_PRIVATE)
        binding.nameView.text = sharedPreferences.getString("NAME","")
        binding.emailView.text = sharedPreferences.getString("EMAIL","")
        binding.ageView.text = sharedPreferences.getString("DATEOFBERTH","")
        binding.langView.text = sharedPreferences.getString("LANG","")
        binding.phoneView.text = sharedPreferences.getString("PHONE","")
        binding.genderView.text = sharedPreferences.getString("GENDER","")


        return binding.root
    }

    }




