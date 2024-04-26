package ir.one_developer.cats.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
        CatsScreenContainer(viewModel = viewModel)
    }

}