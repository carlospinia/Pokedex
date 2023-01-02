package com.pineappleC.pokedex.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.pineapplec.core.ui.theme.PokedexTheme
import com.pineapplec.core.ui.theme.Routes
import com.pineapplec.core.ui.theme.Routes.Companion.POKEMON_ID
import com.pineapplec.pokemon.pokemonDetail.PokemonDetailScreen
import com.pineapplec.pokemon.pokemonList.PokemonListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberAnimatedNavController()
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Routes.PokemonList.id
                    ) {
                        addPokemonList(navController)
                        addPokemonDetail(navController)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    private fun NavGraphBuilder.addPokemonList(navController: NavController) {
        composable(
            route = Routes.PokemonList.id,
            enterTransition = { fadeIn(animationSpec = tween(600)) },
            exitTransition = {
                fadeOut(animationSpec = tween(600))
            }
        )
        {
            PokemonListScreen(navController)
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    private fun NavGraphBuilder.addPokemonDetail(navController: NavController) {
        composable(
            route = Routes.PokemonDetail.id,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Start,
                    tween(
                        durationMillis = 600,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    tween(
                        durationMillis = 600,
                        easing = FastOutLinearInEasing
                    )
                )
            },
            arguments = listOf(navArgument(POKEMON_ID) {
                type = NavType.IntType
            })
        ) {
            PokemonDetailScreen(onBackPressed = { navController.popBackStack() })
        }
    }
}
