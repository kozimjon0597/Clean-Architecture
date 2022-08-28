package uz.kozimjon.cleanarchitecture.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uz.kozimjon.cleanarchitecture.viewmodels.NewsViewModel

//@Module
//interface ViewModelModule {
//
//    @Binds
//    fun bindViewModelFactory(viewModelFactory: ViewModelFactory) : ViewModelProvider.Factory
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(NewsViewModel::class)
//    fun bindNewsViewModel(newsViewModel: NewsViewModel) : ViewModel
//}