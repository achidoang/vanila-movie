package com.kuliah.vanilamovie.presentation.screens.detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kuliah.vanilamovie.domain.model.movie.MovieDetail
import com.kuliah.vanilamovie.domain.model.valueObjects.Company
import com.kuliah.vanilamovie.domain.model.valueObjects.Country
import com.kuliah.vanilamovie.domain.model.valueObjects.Genre
import com.kuliah.vanilamovie.presentation.screens.bioskop.BioskopScreen
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieDetails(
	movie: MovieDetail,
	showMoviePoster: (String) -> Unit,
	onPlayButtonClick: (String) -> Unit
) {

	val pagerState = rememberPagerState()

	Column(
		modifier = Modifier
			.fillMaxWidth()
			.verticalScroll(rememberScrollState())
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
		Spacer(modifier = Modifier.height(10.dp))

		Divider()
//		DataDetailMovie(movie = movie)
				HorizontalPager(
					state = pagerState,
					count = 2,
					modifier = Modifier
						.fillMaxSize()
						.height(800.dp)
				) { page ->
					when (page) {
						0 -> DataDetailMovie(movie = movie)
						1 -> BioskopScreen()
					}
				}

		//		Column(
		//			modifier = Modifier.fillMaxSize(),
		//			horizontalAlignment = Alignment.CenterHorizontally
		//
		//		) {
		//
		//
		//			HorizontalPager(
		//				state = pagerState,
		//				count = 2,
		//				modifier = Modifier.weight(1f)
		//
		//			) { page ->
		//				when (page) {
		//					0 -> {
		//						Text(text = "Data Movie")
		//					}
		//
		//					1 -> {
		//						Text(text = "Booking Movie")
		//
		//					}
		//				}
		//			}
		//		}

	}
}

@Composable
fun DataDetailMovie1(
	onPlayButtonClick: (String) -> Unit,
	movie: MovieDetail
){
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
				Text(text = "Rating    : ")
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
				containerColor = DodgerBlue,
				contentColor = Color.White,
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
@Composable
fun DataDetailMovie(
	movie: MovieDetail,
) {
	if (movie.tagline != "") {
		Text(
			text = movie.tagline ?: "",
			modifier = Modifier
				.fillMaxWidth()
				.padding(14.dp),
			fontStyle = FontStyle.Italic,
			fontWeight = FontWeight.Medium,
			fontSize = 17.sp
		)
	}

	if (!movie.overview.isNullOrEmpty()) {
		Text(
			text = "Overview",
			Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
			fontSize = 18.sp,
			fontWeight = FontWeight.Medium,
			color = SeaGreen
		)
		Text(
			text = movie.overview,
			modifier = Modifier
				.fillMaxWidth()
				.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
		)
	}

	if (!movie.releaseDate.isNullOrEmpty()) {
		Divider()
		Row(
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.Center,
			modifier = Modifier
				.fillMaxWidth()
				.padding(10.dp)
		) {

			if (movie.budget == 0L && movie.revenue == 0L) {
				Row(
					horizontalArrangement = Arrangement.Center,
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						text = "${movie.status}:",
						fontWeight = FontWeight.Bold,
						modifier = Modifier.padding(end = 5.dp)
					)
					//                        Text(
					//                            text = formatDate(movie.releaseDate),
					//                            fontWeight = FontWeight.Bold,
					//                            color = TomatoRed
					//                        )
					Text(
						text = movie.releaseDate,
						fontWeight = FontWeight.Bold,
						color = TomatoRed
					)
				}

			} else {
				Box(
					modifier = Modifier.padding(end = 10.dp)
				) {
					Text(
						text = movie.status?.lowercase()!!,
						fontSize = 12.sp,
						modifier = Modifier.align(Alignment.TopEnd)
					)
					Text(
						text = formatDate(movie.releaseDate),
						modifier = Modifier.padding(top = 15.dp),
						fontWeight = FontWeight.Bold,
						color = TomatoRed,
						maxLines = 1
					)
					//                    Text(
					//                        text = "${movie.releaseDate}",
					//                        modifier = Modifier.padding(top = 15.dp),
					//                        fontWeight = FontWeight.Bold,
					//                        color = PumpkinOrange
					//                    )
				}
			}

			if (movie.budget != 0L) {
				Spacer(
					modifier = Modifier
						.height(24.dp)
						.width(2.dp)
						.background(color = Color.Gray)
						.padding(10.dp)
				)
				Box(
					modifier = Modifier.padding(start = 10.dp, end = 10.dp)
				) {
					Text(
						text = "input",
						fontSize = 12.sp,
						modifier = Modifier.align(Alignment.TopEnd)
					)
					Text(
						text = "$${separateWithCommas(movie.budget)}",
						modifier = Modifier.padding(top = 15.dp),
						fontWeight = FontWeight.Bold,
						color = TomatoRed,
						maxLines = 1
					)
				}
			}
			if (movie.revenue != 0L) {
				Spacer(
					modifier = Modifier
						.height(24.dp)
						.width(2.dp)
						.background(color = Color.Gray)
						.padding(10.dp)
				)
				Box(
					modifier = Modifier.padding(start = 10.dp, end = 10.dp)
				) {
					Text(
						text = "output",
						fontSize = 12.sp,
						modifier = Modifier.align(Alignment.TopEnd)
					)
					Text(
						text = "$${separateWithCommas(movie.revenue)}",
						modifier = Modifier.padding(top = 15.dp),
						fontWeight = FontWeight.Bold,
						color = TomatoRed,
						maxLines = 1
					)
				}
			}
		}
		Divider()
		Spacer(modifier = Modifier.height(10.dp))
	}

	//	1

	Column(
		modifier = Modifier.padding(horizontal = 10.dp)
	) {
		if (movie.productionCompanies.isNotEmpty()) {
			Text(
				text = "Production Companies:",
				fontWeight = FontWeight.Bold,
				modifier = Modifier.padding(bottom = 4.dp),
				color = SeaGreen
			)
			Text(text = movie.productionCompanies.joinToString { it.name })
			Spacer(modifier = Modifier.size(12.dp))
		}
		if (movie.productionCountries.isNotEmpty()) {
			Text(
				text = "Production Countries:",
				fontWeight = FontWeight.Bold,
				modifier = Modifier.padding(bottom = 4.dp),
				color = SeaGreen
			)
			Text(text = movie.productionCountries.joinToString { it.name })
		}
	}
	Spacer(modifier = Modifier.size(10.dp))
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DetailPreview() {
	VanilaMovieTheme {
		MovieDetails(
			onPlayButtonClick = {},
			showMoviePoster = {},
			movie = MovieDetail(
				id = 1,
				budget = 10000,
				genres = listOf(
					Genre(id = 1, name = "Science"),
					Genre(id = 2, name = "Action"),
					Genre(id = 3, name = "Mystery"),
				),
				language = "English",
				title = "Bandland Hunters",
				overview = "After a deadly earthquake turns Seoul into a lawless badland, a fearless huntsman springs into action to rescue a teenager abducted by a mad doctor.",
				posterPath = null,
				backdropPath = null,
				productionCountries = listOf(
					Country("Japan"),
					Country("Singapore")
				),
				productionCompanies = listOf(
					Company(id = 1, logoPath = null, name = "Netflix")
				),
				releaseDate = "2023-15-05",
				revenue = 20000L,
				duration = "105",
				status = "Released",
				tagline = "One last hunt to save us all",
				rating = 6.4
			)
		)
	}
}