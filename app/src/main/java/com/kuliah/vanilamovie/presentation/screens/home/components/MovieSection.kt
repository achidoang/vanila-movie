package com.kuliah.vanilamovie.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.kuliah.vanilamovie.domain.model.movie.Movie
import com.kuliah.vanilamovie.presentation.common.AnimatedImageShimmerEffect
import com.kuliah.vanilamovie.presentation.common.PosterImage
import com.kuliah.vanilamovie.presentation.common.PosterWithText

@Composable
fun MoviesSection(
	modifier: Modifier = Modifier,
	movies: LazyPagingItems<Movie>,
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

			when (movies.loadState.refresh) {
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

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MoviesSectionNow(
	modifier: Modifier = Modifier,
	movies: LazyPagingItems<Movie>,
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

			when (movies.loadState.refresh) {
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
							PosterWithText(
								imageUrl = "${it.posterPath}",
								width = 151.dp,
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
