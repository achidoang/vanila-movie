package com.kuliah.vanilamovie.presentation.screens.bioskop

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MovieCreation
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuliah.vanilamovie.domain.model.movie.MovieDetail
import com.kuliah.vanilamovie.presentation.common.PageIndicatorBioskop
import com.kuliah.vanilamovie.presentation.theme.SeaGreen
import com.kuliah.vanilamovie.presentation.theme.TomatoRed
import com.kuliah.vanilamovie.presentation.theme.VanilaMovieTheme
import com.kuliah.vanilamovie.util.formatDate
import com.kuliah.vanilamovie.util.separateWithCommas
import java.time.LocalDate
import java.time.format.TextStyle




@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailDataMovie(movie: MovieDetail) {

	val currentPage =
		"Sinopsis" // Set the current page to "Shows" by default or based on your logic


	Column(
		verticalArrangement = Arrangement.Top,
		horizontalAlignment = Alignment.Start
	) {
		PageIndicatorBioskop(
			currentPage = currentPage,
			onIndicatorClick = {
				// Handle page change if needed
			},
		)
		if (movie.tagline != "") {
			Text(
				text = movie.tagline ?: "",
				modifier = Modifier
					.fillMaxWidth()
					.padding(bottom = 14.dp, start = 10.dp),
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
					.padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
				color = MaterialTheme.colorScheme.onBackground
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
	}
}

@Composable
fun TimeComp(
	time: String,
	isSelected: Boolean = false,
	onClick: (String) -> Unit = {}
){
	val color = when {
		isSelected -> Yellow
		else -> MaterialTheme.colorScheme.background
	}

	val textBg = when {
		isSelected -> Black
		else -> MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
	}


	Surface(
		modifier = Modifier
			.wrapContentSize()
			.width(50.dp)
			.height(40.dp)
			.clip(RoundedCornerShape(16.dp))
			.clickable {
				onClick(time)
			}, shape = RoundedCornerShape(16.dp), color = color
	) {
			Text(
				text = time,
				style = MaterialTheme.typography.labelSmall,
				color = textBg,
				modifier = Modifier.padding(12.dp))


	}
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateComp(
	date: LocalDate,
	isSelected: Boolean = false,
	onClick: (LocalDate) -> Unit = {},
) {
	val color = when {
		isSelected -> Yellow
		else -> MaterialTheme.colorScheme.background
	}

	val textBg = when {
		isSelected -> Black
		else -> MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
	}

	Surface(
		modifier = Modifier
			.wrapContentSize()
			.clip(RoundedCornerShape(16.dp))
			.width(75.dp)
			.clickable {
				onClick(date)
			}, shape = RoundedCornerShape(16.dp), color = color
	) {
		Column(
			modifier = Modifier.padding(12.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			Column {
				Text(
					text = date.month.getDisplayName(
						TextStyle.SHORT,
						java.util.Locale.getDefault()
					),
					style = MaterialTheme.typography.labelSmall,
					color = textBg
				)
				Text(
					text = date.dayOfMonth.toString(),
					style = MaterialTheme.typography.labelSmall,
					color = textBg,
					textAlign = TextAlign.Center
				)

			}
//			Box(
//				modifier = Modifier
//					.clip(RoundedCornerShape(16.dp))
//					.background(textBg)
//					.padding(6.dp)
//			) {
//			}
		}
	}
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun BioskopScreenPreview() {
	VanilaMovieTheme {

	}
}