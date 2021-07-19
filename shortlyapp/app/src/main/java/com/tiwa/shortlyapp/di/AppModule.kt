package com.tiwa.movies.di

import android.content.Context
import androidx.room.Room
import com.tiwa.common.constant.Constants
import com.tiwa.common.constant.Constants.DATABASE_NAME
import com.tiwa.common.dao.MovieDao
import com.tiwa.common.dao.MovieDatabase
import com.tiwa.common.repository.MovieRepositoryImpl
import com.tiwa.common.service.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room
            .databaseBuilder(
                context,
                MovieDatabase::class.java,
                DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDAO(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun provideDefaultMovieRepository(
        movieDao: MovieDao,
        movieApi: MovieService,
    ): MovieRepositoryImpl{
        return MovieRepositoryImpl(movieDao, movieApi)
    }

    @Singleton
    @Provides
    fun provideMovieService(): MovieService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(MovieService::class.java)
    }


}

