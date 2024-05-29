package com.kuliah.vanilamovie.presentation.navigation

import android.os.Build
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kuliah.vanilamovie.presentation.screens.detail.MovieDetailScreen
import com.kuliah.vanilamovie.presentation.screens.detail.MovieNowDetailScreen
import com.kuliah.vanilamovie.presentation.screens.detail.ShowDetailScreen
import com.kuliah.vanilamovie.presentation.screens.genres.GenreShowsResultScreen
import com.kuliah.vanilamovie.presentation.screens.genres.GenresMovieResultScreen
import com.kuliah.vanilamovie.presentation.screens.home.HomeScreen
import com.kuliah.vanilamovie.presentation.screens.player.PlayerScreen
import com.kuliah.vanilamovie.presentation.screens.poster.PosterScreen
import com.kuliah.vanilamovie.presentation.screens.profile.ProfileScreen
import com.kuliah.vanilamovie.presentation.screens.search.MoviesSearchResultScreen
import com.kuliah.vanilamovie.presentation.screens.search.SearchPager
import com.kuliah.vanilamovie.presentation.screens.search.SearchScreen
import com.kuliah.vanilamovie.presentation.screens.search.ShowsSearchResultScreen
import com.kuliah.vanilamovie.presentation.screens.seat.SeatSelectorScreen
import com.kuliah.vanilamovie.presentation.screens.shows.ShowsScreen
import com.kuliah.vanilamovie.presentation.screens.ticket.TicketScreen
import com.kuliah.vanilamovie.presentation.viewModel.genres.GenresMovieResultViewModelAssistedFactory
import com.kuliah.vanilamovie.presentation.viewModel.genres.GenresShowsResultViewModelAssistedFactory
import com.kuliah.vanilamovie.presentation.viewModel.movie.MovieDetailScreenViewModelAssistedFactory
import com.kuliah.vanilamovie.presentation.viewModel.movie.MoviesSearchResultScreenViewModelAssistedFactory
import com.kuliah.vanilamovie.presentation.viewModel.player.PlayerScreenViewModel
import com.kuliah.vanilamovie.presentation.viewModel.share.SharedViewModel
import com.kuliah.vanilamovie.presentation.viewModel.shows.ShowDetailScreenViewModelAssistedFactory
import com.kuliah.vanilamovie.presentation.viewModel.shows.ShowsSearchResultScreenViewModelAssistedFactory

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(UnstableApi::class)
@Composable
fun AppNavigationGraph(
	navHostController: NavHostController,
	moviesGenresAssistedFactory: GenresMovieResultViewModelAssistedFactory,
	showsGenresAssistedFactory: GenresShowsResultViewModelAssistedFactory,
	moviesSearchAssistedFactory: MoviesSearchResultScreenViewModelAssistedFactory,
	showsSearchAssistedFactory: ShowsSearchResultScreenViewModelAssistedFactory,
	movieDetailAssistedFactory: MovieDetailScreenViewModelAssistedFactory,
	showDetailAssistedFactory: ShowDetailScreenViewModelAssistedFactory,
	moviesPlayerAssistedFactory: PlayerScreenViewModel.PlayerViewModelAssistedFactory,
	modifier: Modifier,
	sharedViewModel: SharedViewModel,
	darkTheme: Boolean
) {
	NavHost(
		navController = navHostController,
		startDestination = Route.Home.destination
	) {

		composable(Route.Home.destination) {
			HomeScreen(
				modifier = Modifier,
				showMovieDetail = {
					navHostController.navigate("${Route.MovieDetail.destination}/$it")
				},
				navController = navHostController, // Teruskan navController ke HomeScreen
				darkTheme = darkTheme // Ubah sesuai kebutuhan Anda
			)
		}

		composable(Route.Profile.destination) {
			ProfileScreen()
		}

		composable(Route.MovieNow.destination){
			MovieNowDetailScreen(modifier = modifier, showMovieDetail = {
				navHostController.navigate("${Route.MovieDetail.destination}/$it")
			} )
		}

		composable(Route.Ticket.destination){
			TicketScreen()
		}

		
		composable(Route.Seat.destination){
			SeatSelectorScreen()
		}

		composable(route = Route.Search.destination) {
			SearchScreen(modifier = modifier, searchMovies = {
				navHostController.navigate("${Route.SearchMovies.destination}/$it")
			}, showMovieDetail = {
				navHostController.navigate("${Route.MovieDetail.destination}/$it")
			}, fetchMoviesByGenre = {navHostController.navigate("${Route.MoviesGenreResult.destination}/$it")})
		}

		composable(
			route = "${Route.SearchMovies.destination}/{query}",
			arguments = listOf(
				navArgument("query") { type = NavType.StringType }
			)
		) {
			val query = it.arguments?.getString("query")!!
			MoviesSearchResultScreen(
				query,
				moviesSearchAssistedFactory,
				modifier,
				showMovieDetail = { movieId ->
					navHostController.navigate("${Route.MovieDetail.destination}/$movieId")
				},
				showMoviePoster = { posterPath ->
					sharedViewModel.putPosterPath(posterPath)
					navHostController.navigate(Route.PosterImage.destination)
				})
		}

		composable(route = Route.Shows.destination) {
			ShowsScreen(modifier = modifier, searchShows = {
				navHostController.navigate("${Route.SearchShows.destination}/$it")
			}, showDetails = { showId ->
				navHostController.navigate("${Route.ShowDetail.destination}/$showId")
			}, fetchShowsByGenre = {navHostController.navigate("${Route.ShowsGenreResult.destination}/$it")})
		}

		composable(route = Route.Pager.destination) {
			SearchPager(
				searchMovies = { navHostController.navigate("${Route.SearchMovies.destination}/$it") },
				showMovieDetail = { navHostController.navigate("${Route.MovieDetail.destination}/$it") },
				searchShows = {navHostController.navigate("${Route.SearchShows.destination}/$it")},
				showDetails = { showId ->
					navHostController.navigate("${Route.ShowDetail.destination}/$showId")
				},
				fetchMoviesByGenre = {navHostController.navigate("${Route.MoviesGenreResult.destination}/$it")},
				fetchShowsByGenre = {navHostController.navigate("${Route.ShowsGenreResult.destination}/$it")}
			)
		}

		composable(
			route = "${Route.SearchShows.destination}/{q}",
			arguments = listOf(
				navArgument(name = "q") { type = NavType.StringType }
			)
		) {
			val query = it.arguments?.getString("q")!!
			ShowsSearchResultScreen(
				query = query,
				assistedFactory = showsSearchAssistedFactory,
				modifier = modifier,
				showDetails = {
					navHostController.navigate("${Route.ShowDetail.destination}/$it")
				},
				showPoster = {
					sharedViewModel.putPosterPath(it)
					navHostController.navigate(Route.PosterImage.destination)
				})
		}


		composable(
			route = Route.MoviesGenreResult.destination + "/{id}",
			arguments = listOf(
				navArgument(name = "id") {
					type = NavType.LongType
				}
			)
		) {
			val id = it.arguments?.getLong("id")!!
			GenresMovieResultScreen(
				modifier = modifier,
				genreId = id,
				assistedFactory = moviesGenresAssistedFactory,
				showMovieDetail = { movieId ->
					navHostController.navigate("${Route.MovieDetail.destination}/$movieId")
				},
				showMoviePoster = { posterPath ->
					sharedViewModel.putPosterPath(posterPath)
					navHostController.navigate(Route.PosterImage.destination)
				})
		}

		composable(route = Route.PosterImage.destination) {
			PosterScreen(sharedViewModel = sharedViewModel)
		}

		composable(
			route = "${Route.MovieDetail.destination}/{id}",
			arguments = listOf(
				navArgument(name = "id") { type = NavType.IntType }
			)
		) {
			val movieId = it.arguments?.getInt("id")!!
			MovieDetailScreen(
				movieId = movieId,
				modifier = modifier,
				assistedFactory = movieDetailAssistedFactory,
				showMoviePoster = { posterPath ->
					sharedViewModel.putPosterPath(posterPath)
					navHostController.navigate(Route.PosterImage.destination)
				},
				watchVideoPreview = { movieName ->
					navHostController.navigate("${Route.MoviesPlayer.destination}/$movieName")
				},
				navController = navHostController)
		}

		composable(
			route = "${Route.MoviesPlayer.destination}/{name}",
			arguments = listOf(
				navArgument(name = "name") { type = NavType.StringType }
			)
		) {
			val movieName = it.arguments?.getString("name")!!
			PlayerScreen(name = movieName, assistedFactory = moviesPlayerAssistedFactory)
		}

		composable(
			route = "${Route.ShowDetail.destination}/{id}",
			arguments = listOf(
				navArgument(name = "id") { type = NavType.IntType }
			)
		) {
			val showId = it.arguments?.getInt("id")!!
			ShowDetailScreen(
				showId = showId,
				modifier = modifier,
				assistedFactory = showDetailAssistedFactory,
				showPoster = { posterPath ->
					sharedViewModel.putPosterPath(posterPath)
					navHostController.navigate(Route.PosterImage.destination)
				})
		}

		composable(
			route = "${Route.ShowsGenreResult.destination}/{id}",
			arguments = listOf(
				navArgument(name = "id") { type = NavType.LongType }
			)
		) {
			val genreId = it.arguments?.getLong("id")!!
			GenreShowsResultScreen(
				modifier = Modifier.fillMaxSize(),
				assistedFactory = showsGenresAssistedFactory,
				genreId = genreId,
				showPoster = {
					sharedViewModel.putPosterPath(it)
					navHostController.navigate(Route.PosterImage.destination)
				},
				showDetail = { showId ->
					navHostController.navigate("${Route.ShowDetail.destination}/$showId")
				}
			)
		}

	}

}