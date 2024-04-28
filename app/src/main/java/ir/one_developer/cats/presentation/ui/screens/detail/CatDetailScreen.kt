package ir.one_developer.cats.presentation.ui.screens.detail

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ir.one_developer.cats.R
import ir.one_developer.cats.domain.model.Cat
import ir.one_developer.cats.domain.model.createCats
import ir.one_developer.cats.presentation.theme.CatsTheme

@Composable
fun CatDetailScreen(
    item: Cat,
    modifier: Modifier = Modifier,
    onBookmarkClick: (Cat) -> Unit = {}
) = Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
        .clickable(enabled = false, onClick = {})
        .background(color = Color.Black.copy(alpha = .4F))
        .padding(vertical = 92.dp)
        .padding(horizontal = 16.dp)
        .clip(MaterialTheme.shapes.medium)
        .background(color = MaterialTheme.colorScheme.background)
        .padding(vertical = 16.dp)
        .fillMaxSize()
) {
    var isBookmarked by rememberSaveable { mutableStateOf(item.bookmarked) }

    val isInPreviewMode = LocalInspectionMode.current
    val contentDescription = "cat with id ${item.id}"
    val imageModifier = Modifier
        .fillMaxSize()
        .clip(MaterialTheme.shapes.small)

    if (!isInPreviewMode) AsyncImage(
        model = item.imageUrl,
        modifier = imageModifier,
        contentScale = ContentScale.Fit,
        contentDescription = contentDescription
    ) else Image(
        modifier = imageModifier,
        contentScale = ContentScale.Fit,
        contentDescription = contentDescription,
        painter = painterResource(id = R.drawable.logo)
    )

    val bookmarkIcon = if (isBookmarked) {
        Icons.Rounded.Bookmark
    } else {
        Icons.Rounded.BookmarkBorder
    }

    FilledIconButton(
        onClick = {
            isBookmarked = !isBookmarked
            onBookmarkClick(item.copy(bookmarked = isBookmarked))
        },
        modifier = Modifier.align(Alignment.BottomCenter),
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
            contentColor = MaterialTheme.colorScheme.background
        )
    ) {
        Icon(
            imageVector = bookmarkIcon,
            contentDescription = bookmarkIcon.name
        )
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun CatDetailPreview() = CatsTheme {
    CatDetailScreen(item = createCats().first())
}