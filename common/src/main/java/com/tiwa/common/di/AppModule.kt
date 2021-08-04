package com.tiwa.common.di

import android.content.Context
import androidx.room.Room
import com.tiwa.common.constant.Constants
import com.tiwa.common.constant.Constants.DATABASE_NAME
import com.tiwa.common.constant.Constants.TIME_OUT
import com.tiwa.common.data.api.ShortLinkService
import com.tiwa.common.data.dao.ShortLinkDao
import com.tiwa.common.data.database.ShortLinkDatabase
import com.tiwa.common.data.repository.ShortLinkRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMovieDatabase(@ApplicationContext context: Context): ShortLinkDatabase {
        return Room
            .databaseBuilder(
                context,
                ShortLinkDatabase::class.java,
                DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideShortLinkDaoDAO(movieDatabase: ShortLinkDatabase): ShortLinkDao {
        return movieDatabase.shortLinkDao()
    }

    @Singleton
    @Provides
    fun provideDefaultShortLinkRepository(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
        shortLinkDao: ShortLinkDao,
        shortLinkService: ShortLinkService,
    ): ShortLinkRepositoryImpl{
        return ShortLinkRepositoryImpl(defaultDispatcher,shortLinkDao, shortLinkService)
    }

    @Singleton
    @Provides
    fun provideShortLinkService(): ShortLinkService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .build()
            .create(ShortLinkService::class.java)
    }

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .build()
    
}

