package com.example.waleedn_oblig2

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.lang.Exception


class PartyAdapter(private val context: Context, private val partyList: MutableList<AlpacaParty>) :
    RecyclerView.Adapter<PartyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView =
            LayoutInflater.from(context).inflate(R.layout.element, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        try {
            Glide.with(context).load(partyList[position].img).into(holder.image)
            holder.txt_name.text = partyList[position].name
            holder.txt_leader.text = "Leader: " + partyList[position].leader
            holder.viewColor.setBackgroundColor(Color.parseColor(partyList[position].color))
            holder.txt_Votes.text = "Votes: " + partyList[position].votes
        }
        catch (e: Exception)
        {
            Toast.makeText(context,""+e.message, Toast.LENGTH_LONG).show()

        }

    }

    override fun getItemCount(): Int {
        return partyList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.img) as ImageView
        var txt_name: TextView = itemView.findViewById(R.id.name) as TextView
        var txt_leader: TextView = itemView.findViewById(R.id.leaderName) as TextView
        var txt_Votes: TextView = itemView.findViewById(R.id.votes) as TextView
        var viewColor: View = itemView.findViewById(R.id.viewColor)
    }
}