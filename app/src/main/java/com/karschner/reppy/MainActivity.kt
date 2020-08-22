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
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var prefs: SharedPreferences
    private val viewModel: RepresentativeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        prefs.edit {
            putString("state", "PA")
            putString("district", "12")
        }

        representative.addHorizontalLayoutManager()
        senators.addHorizontalLayoutManager()

        representative.adapter = RepresentativeAdapter(JSONArray())
        senators.adapter = RepresentativeAdapter(JSONArray())

        viewModel.representative.observe(this){
            (representative.adapter as RepresentativeAdapter).setRepresentatives(it)
        }

        viewModel.senators.observe(this) {
            (senators.adapter as RepresentativeAdapter).setRepresentatives(it)
        }

        viewModel.init()
    }

    private fun RecyclerView.addHorizontalLayoutManager(){
        val linearManager = LinearLayoutManager(context)
        linearManager.orientation = LinearLayoutManager.HORIZONTAL
        this.layoutManager = linearManager
    }
}