package com.kuliah.vanilamovie.presentation.screens.search

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kuliah.vanilamovie.presentation.theme.Black

@Composable
fun PageIndicator(
	modifier: Modifier = Modifier,
	currentPage: String,
	onIndicatorClick: (String) -> Unit
) {
	Row(
		modifier = modifier
			.padding(16.dp)
			.fillMaxWidth(),
		horizontalArrangement = Arrangement.Center
	) {
		val pages = listOf("Movies", "Shows")
		pages.forEach { page ->
			val targetAlpha = if (page == currentPage) 1f else 0.5f // Adjust alpha for fade effect
			val alpha by animateFloatAsState(targetValue = targetAlpha)

			Text(
				text = page,
				color = MaterialTheme.colorScheme.onBackground,
				modifier = Modifier
					.padding(horizontal = 8.dp)
					.clickable { onIndicatorClick(page) }
					.then(
						if (page == currentPage) Modifier.underline() else Modifier
					)
					.alpha(alpha),
				style = if (page == currentPage) {
					MaterialTheme.typography.bodyLarge
				} else {
					MaterialTheme.typography.bodyLarge
				},
				fontWeight = if (page == currentPage){
					FontWeight.Bold
				} else {
					FontWeight.Normal
				}
			)
		}
	}
}

fun Modifier.underline() = this.then(
	Modifier.drawBehind {
		val strokeWidth = 2.dp.toPx()
		val y = size.height - strokeWidth / 2
		drawLine(
			color = Color(0xFFA9A9A9),
			start = Offset(0f, y),
			end = Offset(size.width, y),
			strokeWidth = strokeWidth
		)
	}
)