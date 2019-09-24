package com.yuan.nyctransit.core.platform

import androidx.fragment.app.Fragment
import com.yuan.nyctransit.AndroidApplication
import com.yuan.nyctransit.core.di.ApplicationComponent

abstract class BaseFragment : Fragment() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AndroidApplication).appComponent
    }
}