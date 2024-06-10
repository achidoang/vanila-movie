@file:JvmName("MovieNowDetailScreenKt")

package com.kuliah.vanilamovie.presentation.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import com.kuliah.vanilamovie.presentation.common.AnimatedLargeImageShimmerEffect
import com.kuliah.vanilamovie.presentation.common.NoInternetComponent
import com.kuliah.vanilamovie.presentation.screens.search.components.MovieNowItem
import com.kuliah.vanilamovie.presentation.viewModel.home.HomeScreenViewModel


@Composable
fun MovieNowScreen(
	modifier: Modifier,
	showMovieDetail: (Int) -> Unit
) {

	val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
	val nowPlayingMovies = homeScreenViewModel.nowPlayingMovies.collectAsLazyPagingItems()

	val themeMode = homeScreenViewModel.themeMode.collectAsState().value

//	val currentPage = "Movies" // Set the current page to "Movies"

	when (nowPlayingMovies.loadState.refresh) {
		is LoadState.Error -> {
			Column(
				modifier = modifier.fillMaxSize(),
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				NoInternetComponent(
					modifier = modifier,
					error = "You're offline!",
					refresh = { nowPlayingMovies.refresh() }
				)
			}
		}

		else -> {
			Column(
				modifier = modifier
			) {
				if (nowPlayingMovies.loadState.refresh == LoadState.Loading) {
					Column(
						modifier = Modifier
							.wrapContentHeight()
							.padding(5.dp)
					) {
						Row {
							AnimatedLargeImageShimmerEffect()
							Spacer(modifier = Modifier.width(7.dp))
							AnimatedLargeImageShimmerEffect()
						}
						Spacer(modifier = Modifier.size(7.dp))
						Row {
							AnimatedLargeImageShimmerEffect()
							Spacer(modifier = Modifier.width(7.dp))
							AnimatedLargeImageShimmerEffect()
						}
					}
				} else {

					Text(text = "Film Bioskop",
						modifier = Modifier.padding(start = 15.dp, top = 10.dp, bottom = 16.dp),
						fontWeight = FontWeight.Bold,
						style = MaterialTheme.typography.titleLarge,
						color = if (themeMode) Color.Yellow else Color(0xFF1A2C50)
					)
					Divider()
					Text(text = "Now Playing",
						modifier = Modifier.padding(start= 15.dp, top = 10.dp, bottom = 12.dp),
						fontWeight = FontWeight.Medium,
						style = MaterialTheme.typography.titleMedium,
						color = MaterialTheme.colorScheme.onBackground
					)

					LazyVerticalGrid(
						columns = GridCells.Fixed(2),
						contentPadding = PaddingValues(start = 15.dp)
					) {
						items(
							count = nowPlayingMovies.itemCount,
							contentType = nowPlayingMovies.itemContentType { "Now Playing" }
						) {
							val movie = nowPlayingMovies[it]
							movie?.let {
								MovieNowItem(
									showMovieDetail = showMovieDetail,
									movie = it
								)
							}
						}
					}
				}
			}
		}
	}
}
