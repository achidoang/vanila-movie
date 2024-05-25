package com.kuliah.vanilamovie.presentation.screens.home.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kuliah.vanilamovie.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banner() {
	val banners = listOf(
		R.drawable.banner1,
		R.drawable.banner2,
		R.drawable.banner3,
	)

	val pagerState = rememberPagerState()
	val bannerIndex = remember{ mutableStateOf(0) }

	LaunchedEffect(pagerState) {
		snapshotFlow { pagerState.currentPage }.collect{page ->
			bannerIndex.value = page
		}
	}

	// Auto Scroll
	LaunchedEffect(Unit) {
		while (true){
			delay(2_000)
			tween<Float>(1000)
			pagerState.animateScrollToPage(
				page = (pagerState.currentPage +1) % pagerState.pageCount
			)
		}

	}

	Box(
		modifier = Modifier
			.fillMaxWidth()
			.height(120.dp)
	) {
		HorizontalPager(
			state = pagerState,
			count = banners.size,
			modifier = Modifier
				.fillMaxWidth()
				.height(120.dp)
		) { index ->
			Image(
				painter = painterResource(id = banners[index]),
				contentDescription = "Banners",
				contentScale = ContentScale.FillBounds,
			)
		}
		Row(
			modifier = Modifier
				.align(Alignment.BottomCenter)
				.padding(8.dp)
		) {
			repeat(banners.size){ index ->
				val height = 8.dp
				val width = if(index == bannerIndex.value) 28.dp else 12.dp
				val color = if(index == bannerIndex.value) Yellow else Gray

				Surface (
					modifier = Modifier
						.padding(end = 6.dp)
						.size(width,height)
						.clip(RoundedCornerShape(20.dp)),
					color = color
				){

				}
			}

		}
	}
}