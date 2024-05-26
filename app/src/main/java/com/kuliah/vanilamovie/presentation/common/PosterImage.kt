package com.kuliah.vanilamovie.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.kuliah.vanilamovie.presentation.theme.MidnightBlack
import com.kuliah.vanilamovie.util.displayPosterImage
import kotlinx.coroutines.Dispatchers
import okhttp3.internal.wait

@Composable
fun PosterImage(
	imageUrl: String,
	height: Dp = 350.dp,
	width: Dp?,
	scaleType: ContentScale = ContentScale.Crop,
	cornerSize: Dp = 12.dp,
	horizontalPadding: Dp = 2.dp,
	onClick: ((Int) -> Unit)? = null,
	id: Int? = null
) {

	val context = LocalContext.current

	val imageRequest = ImageRequest.Builder(context)
		.data(displayPosterImage(imageUrl))
		.dispatcher(Dispatchers.IO)
		.memoryCacheKey(imageUrl)
		.diskCacheKey(imageUrl)
		.diskCachePolicy(CachePolicy.ENABLED)
		.memoryCachePolicy(CachePolicy.ENABLED)
		.build()


	if ( width == null ) {
		AsyncImage(
			modifier = Modifier
				.clip(RoundedCornerShape(cornerSize))
				.height(height)
				.background(MidnightBlack),
			model = imageRequest,
			contentDescription = "Poster Image",
			contentScale = scaleType,
		)
	} else {
		AsyncImage(
			modifier = Modifier
				.clickable { onClick?.invoke(id!!) }
				.padding(horizontal = horizontalPadding)
				.clip(RoundedCornerShape(cornerSize))
				.width(width)
				.height(height),
			model = imageRequest,
			contentDescription = "Poster Image",
			contentScale = scaleType,
		)
	}
}

@Composable
fun PosterWithText(
	imageUrl: String,
	height: Dp = 350.dp,
	width: Dp? = null,
	scaleType: ContentScale = ContentScale.Crop,
	cornerSize: Dp = 8.dp,
	horizontalPadding: Dp = 2.dp,
	onClick: ((Int) -> Unit)? = null,
	id: Int? = null,
	text: String = "                Buy Ticket                "
) {
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.wrapContentHeight()
	) {
		PosterImage(
			imageUrl = imageUrl,
			height = height,
			width = width,
			scaleType = scaleType,
			cornerSize = cornerSize,
			horizontalPadding = 10.dp,
			onClick = onClick,
			id = id
		)

		Box(
			modifier = Modifier
				.fillMaxWidth()
				.align(Alignment.BottomCenter)
				.clip(RoundedCornerShape(bottomStart = cornerSize, bottomEnd = cornerSize))
				.background(Color(0xFF1A2C50))
				.padding(vertical = 10.dp),
			contentAlignment = Alignment.Center
		) {
			Text(
				text = text,
				style = MaterialTheme.typography.bodyMedium.copy(
					color = Yellow,
					fontWeight = FontWeight.Bold
				)
			)
		}
	}
}

