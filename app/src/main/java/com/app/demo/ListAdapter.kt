package com.app.demo

import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.app.demo.marker.MapsActivity
import com.app.demo.model.Data
import com.google.gson.Gson
import kotlinx.android.synthetic.main.list_item.view.*
import org.json.JSONObject
import java.lang.Exception

class ListAdapter(
    private var modelList: MutableList<Data>,
    private var context: Context
) :
    RecyclerView.Adapter<EligibilityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EligibilityViewHolder {
        return EligibilityViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EligibilityViewHolder, position: Int) {
        val model = modelList[position]
        try {
           // holder.itemView.name.text = model.name
            holder.itemView.name.text = model.truckNumber
            val days = (model.lastRunningState.stopStartTime / (1000 * 60 * 60 * 24) % 7)
            holder.itemView.tvDuration.text = days.toString()+" days to ago"
            //holder.itemView.tvSpeed.text = model.NotificationDate

 holder.itemView.rlItem.setOnClickListener {
     context.startActivity(Intent(context, MapsActivity::class.java))
 }


        } catch (e: Exception) {

        }

    }


    override fun getItemCount(): Int = modelList.size

}


class EligibilityViewHolder(viewW: View) :
    RecyclerView.ViewHolder(viewW)