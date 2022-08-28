package uz.kozimjon.cleanarchitecture.utils

import android.app.Application
import io.paperdb.Paper
import uz.kozimjon.cleanarchitecture.di.component.AppComponent
import uz.kozimjon.cleanarchitecture.di.component.DaggerAppComponent
import javax.inject.Inject

//class App @Inject constructor() : Application() {
//    companion object {
//        lateinit var appComponent: AppComponent
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//
//        Paper.init(this)
//        appComponent = DaggerAppComponent.factory().create(this)
//        appComponent.inject(this)
//    }
//}

class App : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        Paper.init(this)
        appComponent = DaggerAppComponent.builder().build()
    }
}