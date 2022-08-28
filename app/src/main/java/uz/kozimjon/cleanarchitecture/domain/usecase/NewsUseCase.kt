package uz.kozimjon.cleanarchitecture.domain.usecase

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import uz.kozimjon.cleanarchitecture.domain.model.NewsResponse
import uz.kozimjon.cleanarchitecture.domain.repository.NewsRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class NewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    fun getNews() : Flow<Result<NewsResponse>> {
        return newsRepository.getNews()
            .map {
                Result.success(it)
            }
            .catch {
                emit(Result.failure(it))
            }.flowOn(Dispatchers.IO)
    }
}