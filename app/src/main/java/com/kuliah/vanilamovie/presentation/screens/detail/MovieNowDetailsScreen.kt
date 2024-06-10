package com.kuliah.vanilamovie.presentation.screens.detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MovieCreation
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.kuliah.vanilamovie.presentation.common.NoInternetComponent
import com.kuliah.vanilamovie.presentation.navigation.Route
import com.kuliah.vanilamovie.presentation.theme.SeaGreen
import com.kuliah.vanilamovie.presentation.viewModel.movie.MovieDetailScreenViewModel
import com.kuliah.vanilamovie.presentation.viewModel.movie.MovieDetailScreenViewModelAssistedFactory
import com.kuliah.vanilamovie.presentation.viewModel.movie.MovieDetailScreenViewModelFactory
import com.kuliah.vanilamovie.util.Resource

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieNowDetailsScreen(
	movieId: Int,
	modifier: Modifier,
	assistedFactory: MovieDetailScreenViewModelAssistedFactory,
	showMoviePoster: (String) -> Unit,
	watchVideoPreview: (String) -> Unit,
	navController: NavHostController
) {

	val detailViewModel =  viewModel(
		modelClass = MovieDetailScreenViewModel::class.java,
		factory = MovieDetailScreenViewModelFactory(
			movieId, assistedFactory
		)
	)

	val movieDetailState = detailViewModel.movie.collectAsState().value

	when( movieDetailState ) {

		is Resource.Loading -> {
			Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
				CircularProgressIndicator( modifier = Modifier.size(50.dp), color = SeaGreen )
			}
		}
		is Resource.Error -> {
			Column(
				modifier = Modifier.fillMaxSize(),
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				NoInternetComponent(modifier = modifier, error = movieDetailState.message, refresh = {
					detailViewModel.getMovieDetail()
				} )
			}
		}
		is Resource.Success -> {
			val movie = movieDetailState.result
			MovieNowDetails(movie = movie, showMoviePoster, onPlayButtonClick = watchVideoPreview, navController = navController)
		}
	}
}