package com.yuan.nyctransit.features.lirr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuan.nyctransit.R
import com.yuan.nyctransit.core.database.StopTime
import kotlinx.android.synthetic.main.schedule_view.view.*

class ScheduleAdapter(private val stopTimeList: List<StopTime>) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.schedule_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = stopTimeList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.train_name.text = stopTimeList[position].stopId
        holder.itemView.train_time.text = stopTimeList[position].arrivalTime
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}