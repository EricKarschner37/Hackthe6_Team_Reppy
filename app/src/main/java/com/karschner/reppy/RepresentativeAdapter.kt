package com.karschner.reppy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.representative_card.view.*
import org.json.JSONArray

class RepresentativeAdapter(private val representatives: JSONArray): RecyclerView.Adapter<RepresentativeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.representative_card, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rep = representatives.getJSONObject(position)
        holder.itemView.name.text = rep.getString("name")

        holder.itemView.setBackgroundColor(
            when (rep.getString("party")){
                "D" -> 0xA00015BC.toInt()
                "R" -> 0xA0DE0100.toInt()
                else -> 0xA0DDDDBB.toInt()
            }
        )

        holder.itemView.party.text = rep.getString("party")
        holder.itemView.next_election.text = rep.getString("next_election")
    }

    override fun getItemCount() = representatives.length()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}