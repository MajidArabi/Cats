package ir.one_developer.cats.presentation.ui.screens.cats

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import ir.one_developer.cats.domain.model.Cat
import ir.one_developer.cats.domain.model.createCats
import ir.one_developer.cats.presentation.core.CatItem
import ir.one_developer.cats.presentation.theme.CatsTheme
import ir.one_developer.cats.presentation.ui.screens.detail.CatDetailScreen
import kotlinx.coroutines.flow.flowOf

@Composable
fun CatsScreenContainer(
    viewModel: CatsViewModel,
    modifier: Modifier = Modifier,
    navigateToBookmarkScreen: () -> Unit
) {
    val items = viewModel.items.collectAsLazyPagingItems()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = items.loadState) {
        viewModel.onLoadStateUpdate(items.loadState)
    }

    CatsScreen(
        items = items,
        state = uiState,
        modifier = modifier,
        onBookmarkClick = viewModel::onBookmarkClick,
        onShowBookmarkListClick = navigateToBookmarkScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CatsScreen(
    state: CatsUiState,
    items: LazyPagingItems<Cat>,
    modifier: Modifier = Modifier,
    onBookmarkClick: (Cat) -> Unit = {},
    onShowBookmarkListClick: () -> Unit = {},
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
                    text = "Spotlight",
                    style = MaterialTheme.typography.bodySmall
                )
            },
            actions = {
                IconButton(
                    onClick = onShowBookmarkListClick,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = CircleShape
                        )
                        .size(36.dp)

                ) {
                    Icon(
                        imageVector = Icons.Rounded.Bookmark,
                        tint = MaterialTheme.colorScheme.background,
                        contentDescription = Icons.Rounded.Bookmark.name
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
        )
        AnimatedVisibility(
            visible = state.loading,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.CenterHorizontally)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        AnimatedVisibility(
            visible = state.notLoading && items.itemCount == 0,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 52.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = state.errorMessage,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(onClick = {
                    items.refresh()
                }) {
                    Text(text = "Refresh")
                }
            }
        }

        AnimatedVisibility(
            visible = !state.error.isNullOrBlank() && !state.loading
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(52.dp)
            ) {
                Text(
                    text = state.error.orEmpty(),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(onClick = {
                    items.refresh()
                }) {
                    Text(text = "Try Again")
                }
            }
        }

        if (!state.loading) LazyVerticalGrid(
            state = lazyGridState,
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Adaptive(100.dp),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                count = items.itemCount,
                key = items.itemKey { it.id }
            ) { index ->
                items[index]?.let { item ->
                    CatItem(cat = item) {
                        selectedCat = item
                    }
                }
            }

            if (state.paginationLoading) item(
                span = { GridItemSpan(1) }
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            if (!state.paginationError.isNullOrBlank() && !state.paginationLoading) item(
                span = { GridItemSpan(1) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = state.paginationError)
                    OutlinedButton(onClick = {
                        items.retry()
                    }) {
                        Text(text = "Try Again")
                    }
                }
            }
        }
    }

    AnimatedVisibility(
        enter = scaleIn(),
        exit = scaleOut(),
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

@Composable
@Preview(showBackground = true)
private fun CatsPreview() = CatsTheme {
    CatsScreen(
        state = CatsUiState(loading = false),
        items = flowOf(PagingData.from(createCats())).collectAsLazyPagingItems(),
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.background
        )
    )
}