package uz.kozimjon.cleanarchitecture.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.kozimjon.cleanarchitecture.domain.model.NewsResponse

interface NewsRepository {
    fun getNews(): Flow<NewsResponse>
}