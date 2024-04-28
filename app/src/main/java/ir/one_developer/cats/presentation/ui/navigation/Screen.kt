package ir.one_developer.cats.presentation.ui.navigation

sealed class Screen(
    val route : String
) {

    data object SplashScreen : Screen(route = "splash.screen")

    data object CatsScreen : Screen(route = "cats.screen")

    data object BookmarkScreen : Screen(route = "bookmark.screen")

}