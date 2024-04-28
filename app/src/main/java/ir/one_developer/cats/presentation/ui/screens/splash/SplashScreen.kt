package ir.one_developer.cats.presentation.ui.screens.splash

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.one_developer.cats.presentation.ui.navigation.Screen
import ir.one_developer.cats.R
import ir.one_developer.cats.presentation.theme.CatsTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreenContainer(
    viewModel: SplashViewModel,
    onNavigate: (screen: Screen) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SplashScreen(
        state = uiState,
        onNavigate = onNavigate,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun SplashScreen(
    state: SplashUiState,
    modifier: Modifier = Modifier,
    onNavigate: (screen: Screen) -> Unit = {}
) = Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
) {

    val scaleLogoAnimation by animateFloatAsState(
        label = "scaleLogoAnimation",
        animationSpec = tween(durationMillis = 1000),
        targetValue = if (state.nextScreen == null) 0F else 1F
    )

    Image(
        modifier = Modifier.size(120.dp).graphicsLayer {
            scaleX = scaleLogoAnimation
            scaleY = scaleLogoAnimation
        },
        contentDescription = "splash-logo",
        painter = painterResource(id = R.drawable.logo),
        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
    )

    LaunchedEffect(key1 = state.nextScreen) {
        if (state.nextScreen != null) {
            delay(1500)
            onNavigate(state.nextScreen)
        }
    }

}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SplashPreview() = CatsTheme {
    SplashScreen(state = SplashUiState())
}