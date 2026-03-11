package edu.ucne.steven_javier_ap2_p2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.steven_javier_ap2_p2.presentation.jugador_entry.JugadorScreen
import edu.ucne.steven_javier_ap2_p2.presentation.jugador_list.JugadorListScreen
import edu.ucne.steven_javier_ap2_p2.presentation.navigation.Screen
import edu.ucne.steven_javier_ap2_p2.ui.theme.Steven_Javier_AP2_P2Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Steven_Javier_AP2_P2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.JugadorList
                    ) {
                        composable<Screen.JugadorList> {
                            JugadorListScreen(
                                onAddJugador = {
                                    navController.navigate(Screen.Jugador())
                                },
                                onJugadorClick = { id ->
                                    navController.navigate(Screen.Jugador(id))
                                }
                            )
                        }
                        composable<Screen.Jugador> { backStackEntry ->
                            val args = backStackEntry.toRoute<Screen.Jugador>()
                            JugadorScreen(
                                jugadorId = args.jugadorId,
                                onNavigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}