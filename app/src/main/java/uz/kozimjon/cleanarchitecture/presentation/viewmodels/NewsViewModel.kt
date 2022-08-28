package uz.kozimjon.cleanarchitecture.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.kozimjon.cleanarchitecture.domain.usecase.NewsUseCase
import uz.kozimjon.cleanarchitecture.utils.NewResource
import javax.inject.Inject

class NewsViewModel @Inject constructor(private val newsUseCase: NewsUseCase) : ViewModel() {

    fun getNews(): StateFlow<NewResource> {
        val stateFlow = MutableStateFlow<NewResource>(NewResource.Loading)

        viewModelScope.launch {
            newsUseCase.getNews()
                .catch {
                    stateFlow.emit(NewResource.Error(it.message ?: ""))
                }
                .collect {
                    if (it.isSuccess) {
                        stateFlow.emit(NewResource.Success(it.getOrNull()))
                    } else if (it.isFailure) {
                        stateFlow.emit(NewResource.Error(it.exceptionOrNull()?.toString()))
                    }
                }
        }

        return stateFlow
    }
}