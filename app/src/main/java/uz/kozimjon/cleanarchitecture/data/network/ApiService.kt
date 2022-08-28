package uz.kozimjon.cleanarchitecture.data.network

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import uz.kozimjon.cleanarchitecture.domain.model.NewsResponse

interface ApiService {

    @GET("/api/v1/products")
    fun getNews(): Flow<NewsResponse>
}