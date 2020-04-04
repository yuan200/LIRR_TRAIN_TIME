package com.yuan.nyctransit.features.lirr.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuan.nyctransit.R
import com.yuan.nyctransit.core.database.Stop
import com.yuan.nyctransit.databinding.SearchFragmentBinding

class SearchFragment : Fragment() {
    private lateinit var stopList: List<Stop>

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val viewModel: SearchViewModel by lazy {
        ViewModelProviders.of(this)[SearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<SearchFragmentBinding>(
            inflater, R.layout.search_fragment,
            container, false
        )
        val root = binding.root
        stopList = mutableListOf()
        val viewManager = LinearLayoutManager(context)
        val stopAdapter = StopAdapter(stopList)
        binding.listView.layoutManager = viewManager
        binding.listView.adapter = stopAdapter
        viewModel.stopList.observe(viewLifecycleOwner, Observer {
            stopAdapter.stopList = it
            stopAdapter.notifyDataSetChanged()
        })
        binding.searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    stopAdapter.filter.filter(newText)
                    return false
                }
            }
        )
        return root
    }
}
