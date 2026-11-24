package com.example.anagramas

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.anagramas.ui.GameScreen
import com.example.anagramas.ui.HomeScreen

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Home,
        modifier = modifier
    ) {
        composable<Home> {
            HomeScreen(
                // Se envía el nombre como argumento a la pantalla del juego
                onPlay = { name -> navController.navigate(route = Game(playerName = name)) }
            )
        }
        composable<Game> { backStackEntry ->
            // Se recibe el argumento
            val args: Game = backStackEntry.toRoute()

            GameScreen(
                playerName = args.playerName,
                onExit = { navController.popBackStack<Home>(inclusive = false) }
            )
        }
    }
}
