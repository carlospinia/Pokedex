package com.pineappleC.pokedex.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pineapplec.core.ui.theme.PokedexTheme
import com.pineapplec.core.ui.theme.Routes
import com.pineapplec.core.ui.theme.Routes.Companion.POKEMON_DETAIL_SPRITE_URL
import com.pineapplec.pokemon.pokemonDetail.PokemonDetailScreen
import com.pineapplec.pokemon.pokemonList.PokemonListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Routes.PokemonList.id
                    ) {
                        composable(Routes.PokemonList.id) {
                            PokemonListScreen(navController)
                        }
                        composable(
                            Routes.PokemonDetail.id,
                            arguments = listOf(navArgument(POKEMON_DETAIL_SPRITE_URL) {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->
                            PokemonDetailScreen(
                                backStackEntry.arguments?.getInt(POKEMON_DETAIL_SPRITE_URL)!!,
                            )
                        }
                    }
                }
            }
        }
    }
}
