package com.kuliah.vanilamovie.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.kuliah.vanilamovie.domain.model.movie.Movie
import com.kuliah.vanilamovie.presentation.common.AnimatedImageShimmerEffect
import com.kuliah.vanilamovie.presentation.common.PosterImage
import com.kuliah.vanilamovie.presentation.common.PosterWithText
import com.kuliah.vanilamovie.presentation.navigation.Route
import com.kuliah.vanilamovie.presentation.viewModel.home.HomeScreenViewModel
import com.kuliah.vanilamovie.presentation.viewModel.movie.MovieDetailScreenViewModel
import com.kuliah.vanilamovie.presentation.viewModel.movie.MovieDetailScreenViewModelAssistedFactory
import com.kuliah.vanilamovie.presentation.viewModel.movie.MovieDetailScreenViewModelFactory

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
	onMovieClick: (Int) -> Unit,
	navController: NavController // Tambahkan parameter NavController
) {

	val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
	val themeMode = homeScreenViewModel.themeMode.collectAsState().value



	Column(
		modifier = modifier.fillMaxWidth()
	) {

		//header
		Row(
			modifier
				.fillMaxSize()
				.padding(start = 10.dp, end = 10.dp, bottom = 6.dp),
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Text(
				text = sectionTitle,
				modifier = Modifier.padding(start = 5.dp, top = 12.dp),
				fontWeight = FontWeight.Bold,
				style = MaterialTheme.typography.titleMedium
			)

			TextButton(onClick = {
				navController.navigate(Route.MovieNow.destination) // Tambahkan navigasi ke MovieNowDetailScreen
			}) {
				Text(
					text = "See All...",
					modifier = Modifier.padding(end = 5.dp),
					fontWeight = FontWeight.Medium,
					style = MaterialTheme.typography.labelMedium,
					color = if (themeMode) Color.Yellow else Color(0xFF1A2C50)
				)
			}
		}

		//list
		LazyRow(
			modifier = Modifier
				.height(400.dp)
				.wrapContentWidth()
				.padding(bottom = 9.dp)
		) {
			when (movies.loadState.refresh) {
				is LoadState.Error -> {
					item {
						Row(
							modifier = Modifier
								//								.fillMaxWidth()
								.padding(horizontal = 15.dp),
							horizontalArrangement = Arrangement.spacedBy(15.dp)
						) {
							repeat(10) {
								AnimatedImageShimmerEffect(height = 300.dp, width = 179.dp)
							}
						}
					}
				}

				LoadState.Loading -> {
					item {
						Row (
							modifier = Modifier
//								.fillMaxWidth()
								.padding(horizontal = 15.dp),
							horizontalArrangement = Arrangement.spacedBy(15.dp)
						){
							repeat(10) {
								AnimatedImageShimmerEffect(height = 300.dp, width = 179.dp)
							}
						}
					}
				}

				is LoadState.NotLoading -> {
					items(
						count = movies.itemCount,
						contentType = movies.itemContentType { "contentType" }
					) {
						movies[it]?.let {
							PosterWithText(
								imageUrl = "${it.posterPath}",
								width = 179.dp,
								height = 300.dp,
								scaleType = ContentScale.FillBounds,
								id = it.id,
								onClick = onMovieClick,
								movie = it
							)
						}
					}
				}
			}
		}
	}
}
