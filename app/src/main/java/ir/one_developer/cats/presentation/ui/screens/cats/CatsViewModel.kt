package ir.one_developer.cats.presentation.ui.screens.cats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.one_developer.cats.domain.model.Cat
import ir.one_developer.cats.domain.usecase.GetCatsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CatsViewModel @Inject constructor(
    getCatsUseCase: GetCatsUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<CatsUiState> = MutableStateFlow(CatsUiState())
    val uiState: StateFlow<CatsUiState> = _uiState.asStateFlow()

    val items: Flow<PagingData<Cat>> = getCatsUseCase().cachedIn(viewModelScope)

    fun onLoadStateUpdate(loadState: CombinedLoadStates) {
        val showLoading = loadState.refresh is LoadState.Loading
        val paginationLoading = loadState.append is LoadState.Loading

        val error = when (val refresh = loadState.refresh) {
            is LoadState.Error -> refresh.error.message
            else               -> null
        }

        val paginationError = when (val append = loadState.append) {
            is LoadState.Error -> append.error.message
            else               -> null
        }

        _uiState.update {
            it.copy(
                error = error,
                loading = showLoading,
                paginationError = paginationError,
                paginationLoading = paginationLoading
            )
        }
    }
}