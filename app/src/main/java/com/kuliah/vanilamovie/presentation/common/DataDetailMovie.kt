package com.kuliah.vanilamovie.presentation.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuliah.vanilamovie.domain.model.movie.MovieDetail
import com.kuliah.vanilamovie.presentation.theme.SeaGreen
import com.kuliah.vanilamovie.presentation.theme.TomatoRed
import com.kuliah.vanilamovie.util.formatDate
import com.kuliah.vanilamovie.util.separateWithCommas

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