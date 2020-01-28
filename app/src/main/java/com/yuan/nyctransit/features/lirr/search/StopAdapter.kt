package com.yuan.nyctransit.features.lirr.search

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable

class StopAdapter(val stopList: List<String>): BaseAdapter(), Filterable {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItem(position: Int)  = stopList[position]

    override fun getItemId(position: Int) = position as Long

    override fun getCount() = stopList.size

    override fun getFilter(): Filter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}