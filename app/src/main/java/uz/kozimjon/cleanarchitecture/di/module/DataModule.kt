package uz.kozimjon.cleanarchitecture.di.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import uz.kozimjon.cleanarchitecture.data.network.ApiService
import uz.kozimjon.cleanarchitecture.data.repository.NewsRepositoryImpl
import uz.kozimjon.cleanarchitecture.domain.repository.NewsRepository
import javax.inject.Singleton

@Module(includes = [DataModule.BindModule::class])
class DataModule {

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Module
    abstract class BindModule {
        @Binds
        abstract fun bindRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
    }
}