package uz.kozimjon.cleanarchitecture.data.repository

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import uz.kozimjon.cleanarchitecture.domain.model.NewsResponse
import uz.kozimjon.cleanarchitecture.domain.repository.NewsRepository
import uz.kozimjon.cleanarchitecture.data.network.ApiService
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val apiService: ApiService) : NewsRepository {
    override fun getNews(): Flow<NewsResponse> {
        return apiService.getNews()
    }
}