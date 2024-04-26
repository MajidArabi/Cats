package ir.one_developer.cats.presentation.ui.screens.splash

import ir.one_developer.cats.presentation.ui.navigation.Screen

data class SplashUiState(
    val error : String? = null,
    val loading : Boolean = true,
    val nextScreen : Screen? = null
)