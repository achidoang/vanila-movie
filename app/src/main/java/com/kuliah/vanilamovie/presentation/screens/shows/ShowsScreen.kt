package com.kuliah.vanilamovie.presentation.screens.shows

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.kuliah.vanilamovie.presentation.common.NoInternetComponent
import com.kuliah.vanilamovie.presentation.common.SearchWidget
import com.kuliah.vanilamovie.presentation.common.PageIndicator
import com.kuliah.vanilamovie.presentation.viewModel.genres.GenresViewModel
import com.kuliah.vanilamovie.presentation.viewModel.shows.ShowsScreenEvent
import com.kuliah.vanilamovie.presentation.viewModel.shows.ShowsViewModel

@Composable
fun ShowsScreen(
	modifier: Modifier,
	searchShows: (String) -> Unit,
	fetchShowsByGenre: (genreId: Long) -> Unit,
	showDetails: (Int) -> Unit
) {
	val showsViewModel: ShowsViewModel = hiltViewModel()
	val topRatedTvShows = showsViewModel.topRatedTvShows.collectAsLazyPagingItems()
	val popularTvShows = showsViewModel.popularTvShows.collectAsLazyPagingItems()
	val onAirTvShows = showsViewModel.onAirTvShows.collectAsLazyPagingItems()
	val currentPage = "Shows" // Set the current page to "Shows" by default or based on your logic

	val genresViewModel: GenresViewModel = hiltViewModel()
	val showsGenres = genresViewModel.showsGenres.collectAsState().value

	var id by remember {
		mutableLongStateOf(0L)
	}


	when (topRatedTvShows.loadState.refresh) {
		is LoadState.Error -> {
			Column(
				modifier = modifier.fillMaxSize(),
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				NoInternetComponent(
					modifier = modifier,
					error = "You're offline!",
					refresh = { topRatedTvShows.refresh() }
				)
			}
		}

		else -> {
			Column(
				modifier = modifier
			) {
				SearchWidget(
					query = showsViewModel.searchQuery,
					placeHolder = "Search shows",
					onCloseClicked = {
						showsViewModel.searchQuery = ""
					},
					onSearchClicked = {
						if (showsViewModel.searchQuery.isNotEmpty()) {
							searchShows(showsViewModel.searchQuery)
						}
					},
					onValueChanged = {
						showsViewModel.onEvent(ShowsScreenEvent.SearchQueryChange(it))
					}
				)
				if (topRatedTvShows.loadState.refresh == LoadState.Loading) {
					Column(
						modifier = Modifier
							.wrapContentHeight()
							.padding(5.dp)
					) {
						Row {
							//							AnimatedLargeImageShimmerEffect()
							Spacer(modifier = Modifier.width(7.dp))
							//							AnimatedLargeImageShimmerEffect()
						}
						Spacer(modifier = Modifier.size(7.dp))
						Row {
							//							AnimatedLargeImageShimmerEffect()
							Spacer(modifier = Modifier.width(7.dp))
							//							AnimatedLargeImageShimmerEffect()
						}
					}
				} else {
					PageIndicator(
						currentPage = currentPage,
						onIndicatorClick = {
							// Handle page change if needed
						},

					)
					Column(
						modifier = modifier
							.fillMaxSize()
							.verticalScroll(rememberScrollState())
					) {
						Text(
							text = "SHOWS GENRES",
							fontWeight = FontWeight.Bold,
							modifier = Modifier.fillMaxWidth(),
							textAlign = TextAlign.Center
						)
						Spacer(modifier = Modifier.size(5.dp))
						Divider()
						Spacer(modifier = Modifier.size(10.dp))
						LazyVerticalStaggeredGrid(
							columns = StaggeredGridCells.Fixed(2),
							contentPadding = PaddingValues(8.dp),
							modifier = Modifier.height(600.dp)
						) {
							items(showsGenres) {
								OutlinedButton(
									onClick = {
										id = it.id
										fetchShowsByGenre(id)
									},
									modifier = Modifier
										.fillMaxWidth()
										.padding(end = 7.dp)
								) {
									Text(text = it.name)
								}
							}
						}
					}

				}

			}
		}
	}



//	if ((topRatedTvShows.loadState.refresh is LoadState.Error) &&
//		(popularTvShows.loadState.refresh is LoadState.Error) &&
//		(onAirTvShows.loadState.refresh is LoadState.Error)
//	) {
//		NoInternetComponent(
//			modifier = modifier.fillMaxSize(),
//			error = "You're Offline!",
//			refresh = {
//				topRatedTvShows.refresh()
//				popularTvShows.refresh()
//				onAirTvShows.refresh()
//			}
//		)
//	} else {
//		Column(
//			modifier = modifier
//				.fillMaxSize()
//				.verticalScroll(state = rememberScrollState(), enabled = true)
//		) {
//			SearchWidget(
//				query = showsViewModel.searchQuery,
//				placeHolder = "Search shows",
//				onCloseClicked = {
//					showsViewModel.searchQuery = ""
//				},
//				onSearchClicked = {
//					if (showsViewModel.searchQuery.isNotEmpty()) {
//						searchShows(showsViewModel.searchQuery)
//					}
//				},
//				onValueChanged = {
//					showsViewModel.onEvent(ShowsScreenEvent.SearchQueryChange(it))
//				}
//			)
//
//
//
//			ShowsSection(
//				shows = onAirTvShows,
//				sectionTitle = "On Air",
//				onShowClick = {
//					showDetails.invoke(it)
//				}
//			)
//			ShowsSection(
//				shows = topRatedTvShows,
//				sectionTitle = "Top Rated",
//				onShowClick = {
//					showDetails.invoke(it)
//				}
//			)
//			ShowsSection(
//				shows = popularTvShows,
//				sectionTitle = "Popular",
//				onShowClick = {
//					showDetails.invoke(it)
//				}
//			)
//		}
//	}
}
