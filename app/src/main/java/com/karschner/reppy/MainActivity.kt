package com.karschner.reppy

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    lateinit var prefs: SharedPreferences
    val viewModel: RepresentativeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        prefs.edit {
            putString("state", "PA")
            putString("district", "12")
        }

        representative.apply{
            val linearManager = LinearLayoutManager(this@MainActivity)
            linearManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = linearManager
        }

        senators.apply{
            val linearManager = LinearLayoutManager(this@MainActivity)
            linearManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = linearManager
        }

        viewModel.representative.observe(this){
            representative.adapter = RepresentativeAdapter(it)
        }

        viewModel.senators.observe(this) {
            senators.adapter = RepresentativeAdapter(it)
        }

        viewModel.init()
    }
}