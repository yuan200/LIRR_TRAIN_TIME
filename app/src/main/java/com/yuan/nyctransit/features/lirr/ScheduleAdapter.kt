package com.yuan.nyctransit.features.lirr

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates

class ScheduleAdapter:
    RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    var collection: MutableList<StopTimeUpdateView> by Delegates.observable(
        mutableListOf()) { _, _, _ -> notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.schedule_view, parent, false)
        val view = ScheduleView(parent.context)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = collection.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.itemView.train_name.text = stopTimeList[position].stopId
        val view = holder.itemView as ScheduleView
        view.trainNameTV.text = collection[position].tripHeadSign
        view.trainTimeTV.text = collection[position].delay.toString()
        view.scheduleTime.text = collection[position].arrivingTime.toString()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}