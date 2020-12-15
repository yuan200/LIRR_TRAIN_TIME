package com.yuan.nyctransit.features.lirr.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yuan.nyctransit.R
import com.yuan.nyctransit.core.database.Stop
import timber.log.Timber

class StopAdapter(var stopList: List<Stop>) :
    RecyclerView.Adapter<StopAdapter.SearchItemViewHolder>(), Filterable {

    private var inflater: LayoutInflater? = null

    private var valueFilter: ValueFilter? = null

    internal var clickListener: (Float, Float) -> Unit = { _, _ -> Timber.i("click>>>>>>>>>>>>>>>>>")}

    override fun getFilter(): Filter {
        if (valueFilter == null) valueFilter = ValueFilter()
        return valueFilter!!
    }

    class SearchItemViewHolder(val item: View) : RecyclerView.ViewHolder(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item, parent, false)
        return SearchItemViewHolder(view)
    }

    override fun getItemCount() = stopList.size

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        val stopNameTV = holder.item.findViewById<TextView>(R.id.stopName)
        stopNameTV.text = stopList[position].stopName
        stopNameTV.setOnClickListener {
            clickListener(
                stopList[position].stopLat.toFloat(),
                stopList[position].stopLon.toFloat()
            )
        }
    }

    private inner class ValueFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val result = FilterResults()

            if (constraint != null && constraint.isNotEmpty()) {
                //todo do i have to assign it?
                result.values = stopList.filter {
                    it.stopName.contains(constraint, true)
                }
            }
            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            stopList = results?.values as List<Stop>
            notifyDataSetChanged()
        }

    }
}