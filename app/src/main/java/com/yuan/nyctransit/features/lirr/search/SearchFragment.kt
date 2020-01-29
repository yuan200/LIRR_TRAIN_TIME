package com.yuan.nyctransit.features.lirr.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.yuan.nyctransit.R
import com.yuan.nyctransit.core.database.LirrGtfsBase
import com.yuan.nyctransit.databinding.SearchFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var stopList: List<String>

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<SearchFragmentBinding>(inflater, R.layout.search_fragment,
            container, false)
        val root = binding.root
        CoroutineScope(Dispatchers.IO).launch {
            stopList = LirrGtfsBase.getInstance(context!!)!!.stopDao().getAllStopName()
            val stopAdapter = StopAdapter(stopList)
            binding.listView.adapter = stopAdapter
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        // TODO: Use the ViewModel
    }


}
