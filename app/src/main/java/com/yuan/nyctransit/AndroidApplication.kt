package com.yuan.nyctransit
import android.app.Application
import com.yuan.nyctransit.core.di.ApplicationComponent
import com.yuan.nyctransit.core.di.ApplicationModule
import com.yuan.nyctransit.core.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class AndroidApplication: Application(), HasAndroidInjector {


    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    val appComponent : ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    private fun injectMembers() = appComponent.inject(this)

}