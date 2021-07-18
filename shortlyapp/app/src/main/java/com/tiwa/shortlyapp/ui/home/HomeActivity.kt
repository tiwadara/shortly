package com.tiwa.shortlyapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tiwa.shortlyapp.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        button_shorten.setOnClickListener{
        }
    }
}