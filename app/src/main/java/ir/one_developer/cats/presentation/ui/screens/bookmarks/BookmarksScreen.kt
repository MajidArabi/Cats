package ir.one_developer.cats.presentation.ui.screens.bookmarks

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import ir.one_developer.cats.domain.model.Cat
import ir.one_developer.cats.presentation.core.CatItem
import ir.one_developer.cats.presentation.ui.screens.cats.CatsUiState
import ir.one_developer.cats.presentation.ui.screens.detail.CatDetailScreen

/**
 * Created by majid on 4/28/24.
 * Copyright (c) 2024 majid. All rights reserved.
 */

@Composable
fun BookmarkScreenContainer(
    viewModel: BookmarkViewModel,
    modifier: Modifier = Modifier
) {
    val items by viewModel.items.collectAsStateWithLifecycle(initialValue = emptyList())

    BookmarksScreen(
        items = items,
        onBookmarkClick = viewModel::onBookmarkClick
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BookmarksScreen(
    items: List<Cat>,
    modifier: Modifier = Modifier,
    onBookmarkClick: (Cat) -> Unit = {},
) = Box(
    modifier = modifier
) {
    val lazyGridState = rememberLazyGridState()
    var selectedCat by remember {
        mutableStateOf<Cat?>(null)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Bookmarks",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        )

        LazyVerticalGrid(
            state = lazyGridState,
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Adaptive(100.dp),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items,
                key = { it.id }
            ) { item ->
                CatItem(cat = item) {
                    selectedCat = item
                }
            }
        }
    }

    AnimatedVisibility(
        enter = scaleIn(),
        exit = scaleOut(animationSpec = tween(durationMillis = 5000)),
        visible = selectedCat != null
    ) {
        selectedCat?.let {
            CatDetailScreen(
                item = it,
                onBookmarkClick = onBookmarkClick
            )
        }
    }

    BackHandler(enabled = selectedCat != null) {
        if (selectedCat != null) {
            selectedCat = null
            return@BackHandler
        }
    }
}
