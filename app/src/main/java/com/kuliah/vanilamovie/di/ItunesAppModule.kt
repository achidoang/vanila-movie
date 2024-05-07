package com.kuliah.vanilamovie.di

import android.app.Application
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.kuliah.vanilamovie.data.remote.services.ItunesApi
import com.kuliah.vanilamovie.data.repository.ItunesRepositoryImpl
import com.kuliah.vanilamovie.domain.repository.ItunesRepository
import com.kuliah.vanilamovie.presentation.viewModel.player.PlayerScreenViewModel
import com.kuliah.vanilamovie.util.Constants.ITUNES_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ItunesAppModule {

	@Singleton
	@Provides
	@JvmStatic
	fun providesItunesApiInstance() : ItunesApi {
		val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
			level = HttpLoggingInterceptor.Level.BASIC
		}
		val httpClient = OkHttpClient().newBuilder().addInterceptor(httpLoggingInterceptor)
		httpClient.readTimeout(60, TimeUnit.SECONDS)

		return Retrofit.Builder()
			.baseUrl(ITUNES_BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.client(httpClient.build())
			.build()
			.create(ItunesApi::class.java)
	}

	@Singleton
	@Provides
	fun providesItunesRepository( api: ItunesApi ) : ItunesRepository = ItunesRepositoryImpl( api )

}