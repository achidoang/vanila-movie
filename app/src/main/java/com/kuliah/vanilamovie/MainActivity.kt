 package com.kuliah.vanilamovie

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.UnstableApi
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kuliah.vanilamovie.presentation.common.AppBottomBar
import com.kuliah.vanilamovie.presentation.navigation.AppNavigationGraph
import com.kuliah.vanilamovie.presentation.theme.VanilaMovieTheme
import com.kuliah.vanilamovie.presentation.viewModel.genres.GenresMovieResultViewModelAssistedFactory
import com.kuliah.vanilamovie.presentation.viewModel.genres.GenresShowsResultViewModelAssistedFactory
import com.kuliah.vanilamovie.presentation.viewModel.home.HomeScreenViewModel
import com.kuliah.vanilamovie.presentation.viewModel.movie.MovieDetailScreenViewModelAssistedFactory
import com.kuliah.vanilamovie.presentation.viewModel.movie.MoviesSearchResultScreenViewModelAssistedFactory
import com.kuliah.vanilamovie.presentation.viewModel.player.PlayerScreenViewModel
import com.kuliah.vanilamovie.presentation.viewModel.share.SharedViewModel
import com.kuliah.vanilamovie.presentation.viewModel.shows.ShowDetailScreenViewModelAssistedFactory
import com.kuliah.vanilamovie.presentation.viewModel.shows.ShowsSearchResultScreenViewModelAssistedFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

 @AndroidEntryPoint
 class MainActivity : ComponentActivity() {

	 @Inject
	 lateinit var moviesGenreAssistedFactory: GenresMovieResultViewModelAssistedFactory

	 @Inject
	 lateinit var searchAssistedFactory: MoviesSearchResultScreenViewModelAssistedFactory

	 @Inject
	 lateinit var showsSearchAssistedFactory: ShowsSearchResultScreenViewModelAssistedFactory

	 @Inject
	 lateinit var movieDetailAssistedFactory: MovieDetailScreenViewModelAssistedFactory

	 @Inject
	 lateinit var showDetailAssistedFactory: ShowDetailScreenViewModelAssistedFactory

	 @UnstableApi
	 @Inject
	 lateinit var moviesPlayerAssistedFactory: PlayerScreenViewModel.PlayerViewModelAssistedFactory

	 @Inject
	 lateinit var showsResultAssistedFactory: GenresShowsResultViewModelAssistedFactory

	 @RequiresApi(Build.VERSION_CODES.O)
	 override fun onCreate(savedInstanceState: Bundle?) {
		 super.onCreate(savedInstanceState)
		 installSplashScreen()
		 setContent {
			 val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
			 val themeMode = homeScreenViewModel.themeMode.collectAsState().value

			 VanilaMovieTheme(darkTheme = themeMode) {
				 // Create a SystemUiController instance
				 val systemUiController = rememberSystemUiController()
				 val useDarkIcons = !themeMode

				 // Set the status bar color
				 SideEffect {
					 systemUiController.setSystemBarsColor(
						 color = if (themeMode) Color(0xFF1B1A1F) else Color.White,
						 darkIcons = useDarkIcons
					 )
				 }

				 Surface(
					 modifier = Modifier
						 .fillMaxSize()
						 .navigationBarsPadding()
						 .statusBarsPadding(),
					 color = MaterialTheme.colorScheme.background
				 ) {
					 MainScreen(
						 darkTheme = themeMode
					 )
				 }
			 }
		 }
	 }

	 @RequiresApi(Build.VERSION_CODES.O)
	 @OptIn(ExperimentalMaterial3Api::class)
	 @Composable
	 fun MainScreen(
		 darkTheme: Boolean
	 ) {
		 val navController = rememberNavController()
		 val sharedViewModel: SharedViewModel = viewModel()
		 Scaffold(
			 bottomBar = { AppBottomBar(navController = navController) }
		 ) {
			 AppNavigationGraph(
				 navHostController = navController,
				 modifier = Modifier.padding(paddingValues = it),
				 moviesSearchAssistedFactory = searchAssistedFactory,
				 moviesGenresAssistedFactory = moviesGenreAssistedFactory,
				 showsSearchAssistedFactory = showsSearchAssistedFactory,
				 movieDetailAssistedFactory = movieDetailAssistedFactory,
				 showDetailAssistedFactory = showDetailAssistedFactory,
				 moviesPlayerAssistedFactory = moviesPlayerAssistedFactory,
				 sharedViewModel = sharedViewModel,
				 darkTheme = darkTheme,
				 showsGenresAssistedFactory = showsResultAssistedFactory
			 )
		 }
	 }
 }