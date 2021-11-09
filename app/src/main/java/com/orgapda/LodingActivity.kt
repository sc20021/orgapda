package com.orgapda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.orga_pda.R

class LodingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loding)
        startLoading()
    }
    fun startLoading(){
        val handler = Handler()
        handler.postDelayed({ finish() }, 2000)

    }
}