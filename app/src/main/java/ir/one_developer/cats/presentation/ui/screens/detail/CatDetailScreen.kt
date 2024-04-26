package ir.one_developer.cats.presentation.ui.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import ir.one_developer.cats.R
import ir.one_developer.cats.domain.model.Cat
import ir.one_developer.cats.domain.model.createCats
import ir.one_developer.cats.presentation.theme.CatsTheme

@Composable
fun CatDetailScreen(
    item: Cat,
    modifier: Modifier = Modifier
) = Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
) {
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
}

@Composable
@Preview(showBackground = true)
private fun CatDetailPreview() = CatsTheme {
    CatDetailScreen(item = createCats().first())
}