package ir.one_developer.cats.presentation.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ir.one_developer.cats.presentation.ui.screens.bookmarks.BookmarkScreenContainer
import ir.one_developer.cats.presentation.ui.screens.bookmarks.BookmarkViewModel
import ir.one_developer.cats.presentation.ui.screens.cats.CatsScreenContainer
import ir.one_developer.cats.presentation.ui.screens.cats.CatsViewModel
import ir.one_developer.cats.presentation.ui.screens.splash.SplashScreenContainer
import ir.one_developer.cats.presentation.ui.screens.splash.SplashViewModel

@Composable
fun RootNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) = NavHost(
    modifier = modifier,
    navController = navController,
    startDestination = Screen.SplashScreen.route
) {

    composable(
        route = Screen.SplashScreen.route
    ) {
        val viewModel: SplashViewModel = hiltViewModel()
        SplashScreenContainer(
            viewModel = viewModel,
            onNavigate = { screen ->
                navController.popBackStack()
                navController.navigate(route = screen.route)
            }
        )
    }

    composable(
        route = Screen.CatsScreen.route
    ) {
        val viewModel: CatsViewModel = hiltViewModel()
        CatsScreenContainer(
            viewModel = viewModel,
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            navigateToBookmarkScreen = {
                navController.navigate(route = Screen.BookmarkScreen.route)
            }
        )
    }

    composable(
        route = Screen.BookmarkScreen.route
    ) {
        val viewModel: BookmarkViewModel = hiltViewModel()
        BookmarkScreenContainer(
            viewModel = viewModel,
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        )
    }

}