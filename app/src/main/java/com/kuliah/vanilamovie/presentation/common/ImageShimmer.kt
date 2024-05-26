package com.kuliah.vanilamovie.presentation.common

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedImageShimmerEffect(
	height: Dp = 240.dp,
	width: Dp= 155.dp,
) {

	val shimmerColors = listOf(
		Color.LightGray.copy(alpha = 0.6f),
		Color.LightGray.copy(alpha = 0.2f),
		Color.LightGray.copy(alpha = 0.6f),
	)

	val transition = rememberInfiniteTransition(label = "")
	val translateAnimation = transition.animateFloat(
		initialValue = 0f,
		targetValue = 400f,
		animationSpec = infiniteRepeatable(
			animation = tween(
				durationMillis = 2200,
				easing = FastOutSlowInEasing
			),
			repeatMode = RepeatMode.Reverse
		), label = ""
	)
	val brush = Brush.linearGradient(
		colors = shimmerColors,
		start = Offset.Zero,
		end = Offset(translateAnimation.value, translateAnimation.value)
	)

	ImageShimmerItem(brush = brush, height = height, width = width)
}

@Composable
fun ImageShimmerItem(
	brush: Brush,
	height: Dp = 240.dp,
	width: Dp= 155.dp,
) {
	Spacer(
		modifier = Modifier
			.height(height)
			.width(width)
			.clip(RoundedCornerShape(12.dp))
			.background(brush)
	)
	Spacer(modifier = Modifier.width(7.dp))
}

@Preview(showBackground = true)
@Composable
fun PreviewImageShimmer() {
	ImageShimmerItem(
		brush = Brush.linearGradient(
			colors = listOf(
				Color.LightGray.copy(alpha = 0.6f),
				Color.LightGray.copy(alpha = 0.2f),
				Color.LightGray.copy(alpha = 0.6f),
			)
		)
	)
}