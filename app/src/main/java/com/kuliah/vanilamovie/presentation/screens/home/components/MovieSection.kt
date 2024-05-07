package com.kuliah.vanilamovie.presentation.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import com.kuliah.vanilamovie.domain.model.movie.Movie
import com.kuliah.vanilamovie.presentation.common.AnimatedImageShimmerEffect
import com.kuliah.vanilamovie.presentation.common.PosterImage

@Composable
fun MoviesSection(
	modifier: Modifier = Modifier,
	movies : LazyPagingItems<Movie>,
	sectionTitle: String,
	onMovieClick: (Int) -> Unit
) {

	Column(
		modifier = modifier.fillMaxWidth()
	) {

		//header
		Text(
			text = sectionTitle,
			modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
			fontWeight = FontWeight.Bold,
			style = MaterialTheme.typography.titleMedium
		)

		//list
		LazyRow(
			modifier = Modifier
				.height(240.dp)
				.wrapContentWidth()
				.padding(bottom = 10.dp)
		) {

			when ( movies.loadState.refresh ) {
				is LoadState.Error -> {
					item {
						Row {
							repeat(10) {
								AnimatedImageShimmerEffect()
							}
						}
					}
				}
				LoadState.Loading -> {
					item {
						Row {
							repeat(10) {
								AnimatedImageShimmerEffect()
							}
						}
					}
				}
				is LoadState.NotLoading -> {

					items(
						count = movies.itemCount,
						//key = nowPlayingMovies.itemKey { it.id },
						contentType = movies.itemContentType { "contentType" }
					) {
						movies[it]?.let {
							PosterImage(
								imageUrl = "${it.posterPath}",
								width = 155.dp,
								height = 240.dp,
								scaleType = ContentScale.FillBounds,
								id = it.id,
								onClick = onMovieClick
							)
						}
					}
				}
			}
		}
	}
}