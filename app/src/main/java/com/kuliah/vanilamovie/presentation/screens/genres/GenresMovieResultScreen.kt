package com.kuliah.vanilamovie.presentation.screens.genres

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import com.kuliah.vanilamovie.presentation.common.AnimatedSearchResultShimmerEffect
import com.kuliah.vanilamovie.presentation.common.MovieItem
import com.kuliah.vanilamovie.presentation.common.NoInternetComponent
import com.kuliah.vanilamovie.presentation.viewModel.genres.GenresMovieResultScreenViewModel
import com.kuliah.vanilamovie.presentation.viewModel.genres.GenresMovieResultScreenViewModelFactory
import com.kuliah.vanilamovie.presentation.viewModel.genres.GenresMovieResultViewModelAssistedFactory

@Composable
fun GenresMovieResultScreen(
	modifier: Modifier,
	genreId: Long,
	assistedFactory: GenresMovieResultViewModelAssistedFactory,
	showMovieDetail: (Int) -> Unit,
	showMoviePoster: (String) -> Unit
) {

	val viewModel =  viewModel(
		modelClass = GenresMovieResultScreenViewModel::class.java,
		factory = GenresMovieResultScreenViewModelFactory(
			genreId, assistedFactory
		)
	)

	val movies = viewModel.movies.collectAsLazyPagingItems()

	if ( movies.loadState.refresh is LoadState.Error ) {
		Column(
			modifier = modifier.fillMaxSize(),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			NoInternetComponent(modifier = modifier, error = "You're offline!", refresh = {
				movies.refresh()
			})
		}
	} else {

		LazyColumn{
			items(
				count = movies.itemCount,
				//key = movies.itemKey { it.id },
				contentType = movies.itemContentType{"contentType" }
			) {
				movies[it]?.let {
					MovieItem(
						onItemClick = showMovieDetail,
						onPosterClick = showMoviePoster,
						movie = it
					)
					Divider()
				}
			}
			if ( movies.loadState.refresh == LoadState.Loading ) {
				item {
					repeat(10) {
						AnimatedSearchResultShimmerEffect()
					}
				}
			}
		}

	}
}