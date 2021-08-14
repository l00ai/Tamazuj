package com.corsuevisionplus.tamazuj.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.corsuevisionplus.tamazuj.databinding.FragmentRateBinding


class RateFragment : Fragment() {
    private lateinit var binding: FragmentRateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = FragmentRateBinding.inflate(inflater,container,false)
        binding = view

        binding.okBtn.setOnClickListener {

        }


        return binding.root
    }


}