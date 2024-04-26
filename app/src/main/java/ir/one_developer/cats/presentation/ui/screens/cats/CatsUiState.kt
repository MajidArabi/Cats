package ir.one_developer.cats.presentation.ui.screens.cats

data class CatsUiState(
    val error: String? = null,
    val loading: Boolean = true,
    val paginationError: String? = null,
    val paginationLoading: Boolean = false
) {

    val notLoading: Boolean
        get() = loading.not() && paginationLoading.not() && error.isNullOrBlank() && paginationError.isNullOrBlank()


    val errorMessage: String
        get() = error ?: paginationError
        ?: "Cats not found! Please make sure you have an internet connection and press the refresh button again."
}