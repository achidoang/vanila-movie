package com.kuliah.vanilamovie.presentation.screens.detail

import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MovieCreation
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kuliah.vanilamovie.domain.model.movie.MovieDetail
import com.kuliah.vanilamovie.domain.model.valueObjects.Company
import com.kuliah.vanilamovie.domain.model.valueObjects.Country
import com.kuliah.vanilamovie.domain.model.valueObjects.Genre
import com.kuliah.vanilamovie.presentation.common.DataDetailMovie
import com.kuliah.vanilamovie.presentation.navigation.Route
import com.kuliah.vanilamovie.presentation.screens.bioskop.DateComp
import com.kuliah.vanilamovie.presentation.screens.bioskop.TimeComp
import com.kuliah.vanilamovie.presentation.screens.search.SearchScreen
import com.kuliah.vanilamovie.presentation.screens.shows.ShowsScreen
import com.kuliah.vanilamovie.presentation.theme.DodgerBlue
import com.kuliah.vanilamovie.presentation.theme.MidnightBlack
import com.kuliah.vanilamovie.presentation.theme.SeaGreen
import com.kuliah.vanilamovie.presentation.theme.TomatoRed
import com.kuliah.vanilamovie.presentation.theme.VanilaMovieTheme
import com.kuliah.vanilamovie.util.convertMinutesToHoursAndMinutes
import com.kuliah.vanilamovie.util.displayOriginalImage
import com.kuliah.vanilamovie.util.displayPosterImage
import com.kuliah.vanilamovie.util.formatDate
import com.kuliah.vanilamovie.util.separateWithCommas
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieDetails(
	movie: MovieDetail,
	showMoviePoster: (String) -> Unit,
	onPlayButtonClick: (String) -> Unit,
	navController: NavHostController
) {


	val scrollState = rememberScrollState()

	Column(
		modifier = Modifier
			.fillMaxWidth()
			.verticalScroll(scrollState)
	) {

		Box(
			modifier = Modifier
				.fillMaxWidth()
				.wrapContentHeight()
		) {
			AsyncImage(
				model = displayOriginalImage(movie.backdropPath),
				contentDescription = "backdrop_image",
				modifier = Modifier
					.align(Alignment.TopStart)
					.fillMaxWidth()
					.height(180.dp)
					.background(MidnightBlack),
				contentScale = ContentScale.Crop
			)

			Row(
				modifier = Modifier
					.padding(top = 150.dp, start = 8.dp, end = 8.dp)
					.fillMaxWidth()
					.height(250.dp),
				verticalAlignment = Alignment.Top,
				horizontalArrangement = Arrangement.Start
			) {
				Card(
					onClick = {
						movie.posterPath?.let { posterPath ->
							showMoviePoster(posterPath)
						}
					},
					modifier = Modifier
						.padding(start = 10.dp, end = 20.dp)
						.width(110.dp)
						.height(180.dp)
						.clip(RoundedCornerShape(8.dp)),
				) {
					AsyncImage(
						modifier = Modifier.background(Color.DarkGray),
						model = displayPosterImage(movie.posterPath),
						contentDescription = "Poster Image",
						contentScale = ContentScale.Crop,
					)
				}

				DataDetailMovie1(onPlayButtonClick = onPlayButtonClick, movie = movie)
			}
		}
		Spacer(modifier = Modifier.height(5.dp))

		Divider()
		DataDetailMovie(movie = movie)

	}
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieNowDetails(
	movie: MovieDetail,
	showMoviePoster: (String) -> Unit,
	onPlayButtonClick: (String) -> Unit,
	navController: NavHostController
) {
	val scrollState = rememberScrollState()

	Box(
		modifier = Modifier
			.fillMaxSize()
			.verticalScroll(scrollState)
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(bottom = 56.dp) // Padding bawah untuk memberi ruang bagi tombol "Buy Ticket"
		) {
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.wrapContentHeight()
			) {
				// Your content here
				AsyncImage(
					model = displayOriginalImage(movie.backdropPath),
					contentDescription = "backdrop_image",
					modifier = Modifier
						.align(Alignment.TopStart)
						.fillMaxWidth()
						.height(180.dp)
						.background(MidnightBlack),
					contentScale = ContentScale.Crop
				)

				Row(
					modifier = Modifier
						.padding(top = 150.dp, start = 8.dp, end = 8.dp)
						.fillMaxWidth()
						.height(250.dp),
					verticalAlignment = Alignment.Top,
					horizontalArrangement = Arrangement.Start
				) {
					Card(
						onClick = {
							movie.posterPath?.let { posterPath ->
								showMoviePoster(posterPath)
							}
						},
						modifier = Modifier
							.padding(start = 10.dp, end = 20.dp)
							.width(110.dp)
							.height(180.dp)
							.clip(RoundedCornerShape(8.dp)),
					) {
						AsyncImage(
							modifier = Modifier.background(Color.DarkGray),
							model = displayPosterImage(movie.posterPath),
							contentDescription = "Poster Image",
							contentScale = ContentScale.Crop,
						)
					}

					DataDetailMovie1(onPlayButtonClick = onPlayButtonClick, movie = movie)

				}
			}
			Spacer(modifier = Modifier.height(5.dp))

			Divider()
			DataDetailMovie(movie = movie)
		}

		// Button "Buy Ticket" di luar Column, diposisikan di bagian bawah layar
		Button(
			onClick = { navController.navigate("${Route.Seat.destination}/${movie.title}")},
			colors = ButtonDefaults.buttonColors(
				containerColor = Color(0xFF1A2C50),
				contentColor = Color.Yellow,
			),
			modifier = Modifier
				.fillMaxWidth()
				.height(56.dp)
				.align(Alignment.BottomCenter), // Memposisikan tombol di bagian bawah layar
			shape = RectangleShape // Mengatur bentuk button menjadi persegi panjang
		) {
			Icon(
				imageVector = Icons.Filled.MovieCreation,
				contentDescription = "Movie Icon",
				modifier = Modifier.padding(end = 8.dp)
			)
			Text(text = "Buy Ticket")
		}
	}
}
@Composable
fun DataDetailMovie1(
	onPlayButtonClick: (String) -> Unit,
	movie: MovieDetail
) {
	Column(
		modifier = Modifier
			.padding(top = 35.dp)
			//			.weight(1f)
			.height(400.dp)
	) {
		Text(
			text = movie.title,
			fontWeight = FontWeight.ExtraBold,
			style = MaterialTheme.typography.titleLarge,
			modifier = Modifier
				.fillMaxWidth(),
			maxLines = 3
		)
		if (movie.rating != 0.toDouble()) {
			Spacer(modifier = Modifier.size(11.dp))
			val rating = String.format("%.1f", movie.rating)
			Row(
				modifier = Modifier
					.fillMaxWidth(),
				verticalAlignment = Alignment.Top
			) {
				Text(text = "Rating    :")
				Text(text = "$rating rating")
			}

		} else {
			Spacer(modifier = Modifier.height(4.dp))
			Row(
				modifier = Modifier
					.fillMaxWidth(),
				verticalAlignment = Alignment.Top
			) {
				Text(text = "Rating    :")
				Text(text = "-")
			}
		}

		if (movie.genres.isNotEmpty()) {
			Spacer(modifier = Modifier.size(4.dp))
			Row(
				modifier = Modifier
					.fillMaxWidth(),
				verticalAlignment = Alignment.Top
			) {
				Text(text = "Genres   : ")
				Text(text = movie.genres.joinToString { it.name })
			}
		} else {
			Spacer(modifier = Modifier.size(4.dp))
			Row(
				modifier = Modifier
					.fillMaxWidth(),
				verticalAlignment = Alignment.Top
			) {
				Text(text = "Genres: ")
				Text(text = "-")
			}
		}


		if (!movie.duration.isNullOrEmpty() && movie.duration != "0") {
			Spacer(modifier = Modifier.height(4.dp))
			Row(
				modifier = Modifier
					.fillMaxWidth(),
				verticalAlignment = Alignment.Top
			) {
				Text(text = "Duration : ")
				Text(text = convertMinutesToHoursAndMinutes(movie.duration.toInt()))
			}
		} else {
			Spacer(modifier = Modifier.height(4.dp))
			Row(
				modifier = Modifier
					.fillMaxWidth(),
				verticalAlignment = Alignment.Top
			) {
				Text(text = "Duration : ")
				Text(text = "-")
			}
		}
		Spacer(modifier = Modifier.weight(1f))
		Button(
			modifier = Modifier
				.defaultMinSize(1.dp, 1.dp)
				.fillMaxWidth(),
			colors = ButtonDefaults.buttonColors(
				containerColor = Color(0xFF1A2C50),
				contentColor = Color.Yellow,
			),
			shape = RoundedCornerShape(6.dp),
			onClick = { onPlayButtonClick(movie.title) },
			contentPadding = PaddingValues(
				start = 20.dp,
				end = 10.dp,
				bottom = 5.dp,
				top = 3.dp
			)
		) {
			Text(text = "Preview", fontSize = 18.sp)
			Icon(
				imageVector = Icons.Rounded.PlayArrow,
				contentDescription = "play button",
				modifier = Modifier.size(33.dp)
			)
		}

	}
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DetailPreview() {
	VanilaMovieTheme {

	}
}