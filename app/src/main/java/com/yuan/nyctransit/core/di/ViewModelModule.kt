package com.yuan.nyctransit.core.di

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuan.nyctransit.ui.dashboard.NearbyViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: NearbyViewModelFactory):
            ViewModelProvider.AndroidViewModelFactory

    @Binds
    @IntoMap
    @ViewModelKey(NearbyViewModel::class)
    internal abstract fun nearbyViewModel(viewModel: NearbyViewModel): AndroidViewModel
}