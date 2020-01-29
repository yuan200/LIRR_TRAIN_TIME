package com.yuan.nyctransit.features.lirr.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.yuan.nyctransit.R
import com.yuan.nyctransit.databinding.RowItemBinding

class StopAdapter(val stopList: List<String>): BaseAdapter(){
    private var inflater: LayoutInflater? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if (inflater == null) {
            inflater = parent!!.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        val rowItemBinding = DataBindingUtil.inflate<RowItemBinding>(inflater!!, R.layout.row_item, parent, false)
        rowItemBinding.stopName.text = stopList[position]
        return rowItemBinding.root
    }

    override fun getItem(position: Int)  = stopList[position]

    override fun getItemId(position: Int) = position as Long

    override fun getCount() = stopList.size

//    override fun getFilter(): Filter {
//    }
}