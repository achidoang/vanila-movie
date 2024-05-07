package com.kuliah.vanilamovie.domain.usecases.theme

import com.kuliah.vanilamovie.data.local.PreferenceRepository
import javax.inject.Inject

class RetrieveThemeModeUseCase @Inject constructor(
	private val prefRepository: PreferenceRepository
) {
	operator fun invoke() = prefRepository.storedThemeModel
}