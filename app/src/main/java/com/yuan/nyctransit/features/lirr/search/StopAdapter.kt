package com.yuan.nyctransit.features.lirr.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import com.yuan.nyctransit.R
import com.yuan.nyctransit.core.database.Stop
import com.yuan.nyctransit.databinding.RowItemBinding

class StopAdapter(var stopList: List<Stop>): BaseAdapter(), Filterable{

    private var inflater: LayoutInflater? = null

    private var valueFilter: ValueFilter? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if (inflater == null) {
            inflater = parent!!.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        val rowItemBinding = DataBindingUtil.inflate<RowItemBinding>(inflater!!, R.layout.row_item, parent, false)
        rowItemBinding.stopName.text = stopList[position].stopName
        return rowItemBinding.root
    }

    override fun getItem(position: Int)  = stopList[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = stopList.size

    override fun getFilter(): Filter {
        if (valueFilter == null) valueFilter = ValueFilter()
        return valueFilter!!
    }

    private inner class ValueFilter: Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val result =  FilterResults()

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