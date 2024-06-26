package com.kuliah.vanilamovie.presentation.screens.home.components

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kuliah.vanilamovie.R

@Composable
fun ThemeSwitcher(
	size: Dp = 150.dp,
	themeMode: Boolean,
	iconSize: Dp = size / 3,
	padding: Dp = 10.dp,
	borderWidth: Dp = 1.dp,
	parentShape: RoundedCornerShape = CircleShape,
	toggleShape: RoundedCornerShape = CircleShape,
	animationSpec: AnimationSpec<Dp> = tween(durationMillis = 300),
	onClick: (Boolean) -> Unit,
	modifier: Modifier = Modifier
) {
	var darkTheme by remember {
		mutableStateOf(themeMode)
	}

	val offset by animateDpAsState(
		targetValue = if (themeMode) size else 0.dp,
		animationSpec = animationSpec, label = ""
	)

	Box(modifier = modifier
		.width(size * 2)
		.height(size)
		.clip(shape = parentShape)
		.clickable {
			darkTheme = !themeMode
			onClick(darkTheme)
		}
		.background(MaterialTheme.colorScheme.secondaryContainer)
	) {
		Box(
			modifier = Modifier
				.size(size)
				.offset(x = offset)
				.padding(all = padding)
				.clip(shape = toggleShape)
				.background(MaterialTheme.colorScheme.primary)
		) {}
		Row(
			modifier = Modifier
				.border(
					border = BorderStroke(
						width = borderWidth,
						color = MaterialTheme.colorScheme.primary
					),
					shape = parentShape
				)
		) {
			Box(
				modifier = Modifier.size(size),
				contentAlignment = Alignment.Center
			) {
				Icon(
					modifier = Modifier.size(iconSize),
					painter = painterResource(id = R.drawable.light_mode),
					contentDescription = "Theme Icon",
					tint = if (themeMode) MaterialTheme.colorScheme.primary
					else MaterialTheme.colorScheme.secondaryContainer
				)
			}
			Box(
				modifier = Modifier.size(size),
				contentAlignment = Alignment.Center
			) {
				Icon(
					modifier = Modifier.size(iconSize),
					painter = painterResource(id = R.drawable.night_mode),
					contentDescription = "Theme Icon",
					tint = if (themeMode) MaterialTheme.colorScheme.secondaryContainer
					else MaterialTheme.colorScheme.primary
				)
			}
		}
	}
}
