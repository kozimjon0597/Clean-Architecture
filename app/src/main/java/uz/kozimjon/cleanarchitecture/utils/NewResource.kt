package uz.kozimjon.cleanarchitecture.utils

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import uz.kozimjon.cleanarchitecture.domain.model.NewsResponse

sealed class NewResource {

    object Loading : NewResource()

    data class Success(val newsResponse: NewsResponse?) : NewResource()

    data class Error(val message: String?) : NewResource()
}