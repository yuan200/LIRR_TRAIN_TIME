package com.yuan.nyctransit.features.lirr

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuan.nyctransit.core.database.StopTime

class ScheduleAdapter(private val stopTimeList: List<StopTime>) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

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
        view.trainNameTV.text = "schedule view"
        view.trainTimeTV.text = "train time tv"
//        holder.itemView.train_time.text = stopTimeList[position].arrivalTime
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}