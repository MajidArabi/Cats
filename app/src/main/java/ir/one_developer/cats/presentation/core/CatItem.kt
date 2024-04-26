package ir.one_developer.cats.presentation.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.BrokenImage
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.imageLoader
import coil.util.DebugLogger
import ir.one_developer.cats.domain.model.Cat
import ir.one_developer.cats.domain.model.createCats
import ir.one_developer.cats.presentation.theme.CatsTheme

@Composable
fun CatItem(
    cat: Cat,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val imageLoader = LocalContext.current
        .imageLoader
        .newBuilder()
        .crossfade(true)
        .logger(DebugLogger())
        .respectCacheHeaders(false)
        .build()

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
            .clip(MaterialTheme.shapes.small)
            .clickable { onClick() }
            .then(modifier)
    ) {
        SubcomposeAsyncImage(
            model = cat.imageUrl,
            imageLoader = imageLoader,
            contentDescription = cat.imageUrl,
            contentScale = ContentScale.Crop,
            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .loading(visible = true)
                )
            },
            error = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(52.dp),
                        imageVector = Icons.TwoTone.BrokenImage,
                        contentDescription = Icons.TwoTone.BrokenImage.name,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(MaterialTheme.shapes.small)
        )
    }
}

@Preview
@Composable
private fun CatItemPreview() = CatsTheme {
    CatItem(cat = createCats().first()) {

    }
}