package com.kuliah.vanilamovie.presentation.screens.poster

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.kuliah.vanilamovie.presentation.viewModel.share.SharedViewModel
import com.kuliah.vanilamovie.util.displayOriginalImage

@Composable
fun PosterScreen(
	sharedViewModel: SharedViewModel,
) {
	val posterPath = sharedViewModel.moviePath

	Box(
		modifier = Modifier
			.fillMaxSize()
		//.background(MaterialTheme.colorScheme.background)
	) {
		AsyncImage(
			modifier = Modifier
				.fillMaxSize(),
			model = displayOriginalImage(posterPath),
			contentDescription = "Poster Image",
			contentScale = ContentScale.Fit,
		)
	}
}