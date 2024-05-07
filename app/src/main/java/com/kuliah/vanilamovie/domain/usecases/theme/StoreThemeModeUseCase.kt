package com.kuliah.vanilamovie.domain.usecases.theme

import com.kuliah.vanilamovie.data.local.PreferenceRepository
import javax.inject.Inject

class StoreThemeModeUseCase @Inject constructor(
	private val prefRepository: PreferenceRepository
) {
	suspend operator fun invoke(themeMode: Boolean ) = prefRepository.storeThemeMode(themeMode)
}