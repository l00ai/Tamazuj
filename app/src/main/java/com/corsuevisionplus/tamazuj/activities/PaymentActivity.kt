package com.corsuevisionplus.tamazuj.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.corsuevisionplus.tamazuj.R
import com.corsuevisionplus.tamazuj.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {
    private lateinit var paymentBinding: ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        paymentBinding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(paymentBinding.root)
        paymentBinding.skipToChat.setOnClickListener {
            startActivity(Intent(this,ChatActivity::class.java))
        }

    }
}