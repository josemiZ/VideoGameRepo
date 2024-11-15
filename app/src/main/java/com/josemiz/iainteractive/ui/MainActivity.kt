package com.josemiz.iainteractive.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.josemiz.iainteractive.ui.navigation.VideoGameNavigation
import com.josemiz.iainteractive.ui.screen.DetailScreen
import com.josemiz.iainteractive.ui.screen.LoadScreen
import com.josemiz.iainteractive.ui.screen.SearchScreen
import com.josemiz.iainteractive.ui.screen.SplashScreen
import com.josemiz.iainteractive.ui.theme.IAInteractiveTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IAInteractiveTheme {
                val controller = rememberNavController()
                NavHost(
                    navController = controller,
                    startDestination = VideoGameNavigation.Splash
                ) {
                    composable<VideoGameNavigation.Splash> {
                        val state by viewModel.uiState.collectAsState()

                        LaunchedEffect(Unit) {
                            viewModel.loadGames()
                        }

                        LaunchedEffect(state.areVideoGamesSaved) {
                            if (state.areVideoGamesSaved) {
                                viewModel.resetAreVideoGamesSaved()
                                controller.navigate(VideoGameNavigation.MainSearch) {
                                    popUpTo(VideoGameNavigation.Splash) { inclusive = true }
                                }
                            }
                        }

                        SplashScreen(modifier = Modifier.fillMaxSize())
                    }

                    composable<VideoGameNavigation.MainSearch> {
                        val state by viewModel.uiState.collectAsState()

                        LaunchedEffect(Unit) {
                            viewModel.getGames()
                        }

                        if (state.loading) {
                            LoadScreen(modifier = Modifier.fillMaxSize())
                        } else {
                            SearchScreen(
                                list = state.videoGames,
                                modifier = Modifier.fillMaxSize(),
                                onValueChange = viewModel::getGames,
                                onGameSelected = {
                                    controller.navigate(
                                        VideoGameNavigation.Detail(
                                            id = it.id,
                                            title = it.title,
                                            thumbnail = it.thumbnail,
                                            shortDescription = it.shortDescription,
                                            gameUrl = it.gameUrl,
                                            genre = it.genre,
                                            platform = it.platform,
                                            publisher = it.publisher,
                                            developer = it.developer,
                                            releaseDate = it.releaseDate,
                                            profileUrl = it.profileUrl
                                        )
                                    )
                                }
                            )
                        }
                    }

                    composable<VideoGameNavigation.Detail> {
                        val args = it.toRoute<VideoGameNavigation.Detail>()
                        val state by viewModel.uiState.collectAsState()

                        LaunchedEffect(state.deleteGame) {
                            if (state.deleteGame) {
                                controller.popBackStack()
                            }
                        }

                        DetailScreen(
                            imageUrl = args.thumbnail,
                            title = args.title,
                            description = args.shortDescription,
                            genre = args.genre,
                            platform = args.platform,
                            creator = args.publisher,
                            developer = args.developer,
                            releaseDate = args.releaseDate,
                            modifier = Modifier.fillMaxSize(),
                            onBackClicked = controller::popBackStack,
                            onDeleteClicked = { viewModel.deleteGame(args.id) }
                        )
                    }
                }
            }
        }
    }
}