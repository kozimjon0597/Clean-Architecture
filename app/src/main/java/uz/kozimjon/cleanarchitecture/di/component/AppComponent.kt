package uz.kozimjon.cleanarchitecture.di.component

import dagger.Component
import uz.kozimjon.cleanarchitecture.presentation.MainActivity
import uz.kozimjon.cleanarchitecture.di.module.DataModule
import uz.kozimjon.cleanarchitecture.di.module.NetworkModule
import uz.kozimjon.cleanarchitecture.presentation.ui.HomeFragment
import uz.kozimjon.cleanarchitecture.utils.App
import javax.inject.Singleton

//@Component(modules = [NetworkModule::class, DataModule::class, ViewModelModule::class])
@Singleton
@Component(modules = [NetworkModule::class, DataModule::class])
interface AppComponent {

//    @Component.Factory
//    interface Factory {
//        fun create (@BindsInstance app : App) : AppComponent
//    }

    fun inject(app: App)
    fun inject(mainActivity: MainActivity)
    fun inject(homeFragment: HomeFragment)
}