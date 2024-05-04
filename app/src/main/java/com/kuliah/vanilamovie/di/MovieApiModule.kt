package com.kuliah.vanilamovie.di

import android.app.Application
import android.content.Context
import androidx.annotation.OptIn
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.kuliah.vanilamovie.data.remote.services.MovieApi
import com.kuliah.vanilamovie.data.repository.MovieRepositoryImpl
import com.kuliah.vanilamovie.data.repository.ShowsRepositoryImpl
import com.kuliah.vanilamovie.domain.repository.MovieRepository
import com.kuliah.vanilamovie.domain.repository.ShowsRepository
import com.kuliah.vanilamovie.util.Constants.MOVIE_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@OptIn(UnstableApi::class)
object MovieApiModule {

	@Singleton
	@Provides
	@JvmStatic
	fun providesMovieApiInstance() : MovieApi {
		val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
			level = HttpLoggingInterceptor.Level.BASIC
		}
		val httpClient = OkHttpClient().newBuilder().addInterceptor(httpLoggingInterceptor)
		httpClient.readTimeout(60, TimeUnit.SECONDS)

		return Retrofit.Builder()
			.baseUrl(MOVIE_BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.client(httpClient.build())
			.build()
			.create(MovieApi::class.java)
	}

	@Singleton
	@Provides
	fun providesMovieRepository( api: MovieApi ) : MovieRepository = MovieRepositoryImpl( api )

	@Singleton
	@Provides
	fun providesShowsRepository( api: MovieApi) : ShowsRepository = ShowsRepositoryImpl( api )

	@Singleton
	@Provides
	fun provideDatastoreInstance(  @ApplicationContext context : Context) = PreferenceDataStoreFactory.create {
		context.preferencesDataStoreFile("settings")
	}

//	@Singleton
//	@Provides
//	fun provideVideoPlayer( app: Application)  = ExoPlayer.Builder( app )
//		.setMediaSourceFactory(DefaultMediaSourceFactory(PlayerScreenViewModel.cacheDataSourceFactory)).build()

}