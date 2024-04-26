package ir.one_developer.cats.presentation.core

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun Modifier.loading(
    visible: Boolean,
    color: Color = Color.LightGray,
    shape: Shape = MaterialTheme.shapes.small,
    highlight: PlaceholderHighlight = PlaceholderHighlight.shimmer()
) = then(
    placeholder(
        shape = shape,
        color = color,
        visible = visible,
        highlight = highlight
    )
)