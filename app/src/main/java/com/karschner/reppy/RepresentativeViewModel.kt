package com.karschner.reppy

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Math.min

class RepresentativeViewModel(application: Application) : AndroidViewModel(application) {
    val TAG = "RepresentativeViewModel"
    val prefs = application.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    val mainHandler = Handler(Looper.getMainLooper())

    val representative: MutableLiveData<JSONArray> = MutableLiveData()
    val senators: MutableLiveData<JSONArray> = MutableLiveData()

    fun init() {
        Log.i(TAG, "Init")

        GovDataAPI.getHouseRepresentative(
            prefs.getString("state", "")!!,
            prefs.getString("district", "")!!)
            { rep ->
                Log.i(TAG, rep.toString())
                mainHandler.post {
                    representative.value = rep
                }
                getVotingInfo(rep)
            }

        GovDataAPI.getSenators(
            prefs.getString("state", "")!!)
            { senators ->
                Log.i(TAG, senators.toString())
                mainHandler.post {
                    this.senators.value = senators
                }
                getVotingInfo(senators)
            }
    }

    private fun getVotingInfo(representatives: JSONArray){
        for (i in 0 until 5.coerceAtMost(representatives.length())){
            val rep = representatives.getJSONObject(i)
            GovDataAPI.getRecentVotingRecords(rep.getString("id")) {
                rep.put("recent_votes", it)
                Log.i(TAG, rep.toString())
            }
        }
    }
}