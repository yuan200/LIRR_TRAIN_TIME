package com.yuan.nyctransit.features.lirr

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.transit.realtime.GtfsRealtime

class ScheduleAdapter(private val stopTimeList: MutableList<GtfsRealtime.TripUpdate.StopTimeUpdate>):
    RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.schedule_view, parent, false)
        val view = ScheduleView(parent.context)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = stopTimeList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.itemView.train_name.text = stopTimeList[position].stopId
        val view = holder.itemView as ScheduleView
        view.trainNameTV.text = stopTimeList[position].stopId
        view.trainTimeTV.text = stopTimeList[position].arrival.delay.toString()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}