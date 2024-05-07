package com.kuliah.vanilamovie.presentation.screens.detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuliah.vanilamovie.presentation.common.NoInternetComponent
import com.kuliah.vanilamovie.presentation.theme.SeaGreen
import com.kuliah.vanilamovie.presentation.viewModel.shows.ShowDetailScreenViewModel
import com.kuliah.vanilamovie.presentation.viewModel.shows.ShowDetailScreenViewModelAssistedFactory
import com.kuliah.vanilamovie.presentation.viewModel.shows.ShowDetailScreenViewModelFactory
import com.kuliah.vanilamovie.util.Resource

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowDetailScreen(
	showId: Int,
	modifier: Modifier,
	assistedFactory: ShowDetailScreenViewModelAssistedFactory,
	showPoster: (String) -> Unit,
) {

	val viewModel =  viewModel(
		modelClass = ShowDetailScreenViewModel::class.java,
		factory = ShowDetailScreenViewModelFactory(
			showId, assistedFactory
		)
	)

	val showDetailState = viewModel.show.collectAsState().value

	when( showDetailState ) {

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
				NoInternetComponent(modifier = modifier, error = showDetailState.message, refresh = {
					viewModel.getShowDetail(showId)
				} )
			}
		}
		is Resource.Success -> {
			val show = showDetailState.result
			ShowDetails(show = show, showPoster = showPoster)
		}
	}
}