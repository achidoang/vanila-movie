package com.kuliah.vanilamovie.presentation.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.kuliah.vanilamovie.presentation.screens.shows.ShowsScreen


@OptIn(ExperimentalPagerApi::class)
@Composable
fun SearchPager(
	modifier: Modifier = Modifier,
	searchMovies: (String) -> Unit,
	showMovieDetail: (Int) -> Unit,
	searchShows: (String) -> Unit,
	fetchMoviesByGenre: ( genreId: Long ) -> Unit,
	fetchShowsByGenre: ( genreId: Long ) -> Unit,
	showDetails: (Int) -> Unit
) {
	val pagerState = rememberPagerState()

	Column(
		modifier = modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally
	) {

		HorizontalPager(
			state = pagerState,
			count = 2,
			modifier = Modifier.weight(1f)
		) { page ->
			when (page) {
				0 -> {
					SearchScreen(
						modifier = Modifier
							.fillMaxSize(),
						searchMovies = searchMovies,
						showMovieDetail = showMovieDetail,
						fetchMoviesByGenre = fetchMoviesByGenre
					)
				}
				1 -> {
					ShowsScreen(
						modifier = Modifier
							.fillMaxSize(),
						searchShows = searchShows,
						showDetails = showDetails,
						fetchShowsByGenre = fetchShowsByGenre
					)
				}
			}
		}

	}
}