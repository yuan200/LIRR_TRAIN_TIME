package com.yuan.nyctransit.features.lirr

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime
import timber.log.Timber
import kotlin.properties.Delegates

class ScheduleAdapter :
    RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    var collection: MutableList<StopTimeUpdateView> by Delegates.observable(
        mutableListOf()
    ) { _, _, _ -> notifyDataSetChanged() }

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
        if (position == 0) view.firstView = true
        view.routeColor = collection[position].routeColor
        view.tripHeadSignTV.text = collection[position].tripHeadSign
        view.scheduleTime.text = collection[position].arrivingTimeStr.toString()
        view.stopTV.text = collection[position].stopName

        var now = LocalDateTime.now()
        var duration = Duration.between(now, collection[position].arrivingTime)
        Timber.i("duration: ${duration.toString()}")
        view.setRealTimeMuinutes(duration.toMinutes().toString())
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}