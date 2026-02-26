package com.probeaufgabe.catapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.probeaufgabe.catapi.presentation.breeds.BreedListScreen
import com.probeaufgabe.catapi.presentation.detail.BreedDetailScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "breed_list"
                    ) {
                        composable("breed_list") {
                            BreedListScreen(
                                onNavigateToDetail = { clickedBreedId ->
                                    navController.navigate("breed_detail/$clickedBreedId")
                                }
                            )
                        }
                        composable(
                            route = "breed_detail/{breedId}",
                            arguments = listOf(
                                navArgument("breedId") { type = NavType.StringType }
                            )
                        ) {
                            BreedDetailScreen(
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
