package ir.one_developer.cats.presentation.ui.screens.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.one_developer.cats.domain.model.Cat
import ir.one_developer.cats.domain.usecase.BookmarkCatUseCase
import ir.one_developer.cats.domain.usecase.GetBookmarkedCatsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by majid on 4/28/24.
 * Copyright (c) 2024 majid. All rights reserved.
 */

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    getBookmarkedCatsUseCase: GetBookmarkedCatsUseCase,
    private val bookmarkCatUseCase: BookmarkCatUseCase
) : ViewModel() {

    val items: Flow<List<Cat>> = getBookmarkedCatsUseCase()

    fun onBookmarkClick(cat: Cat) {
        viewModelScope.launch {
            bookmarkCatUseCase.invoke(cat)
        }
    }

}