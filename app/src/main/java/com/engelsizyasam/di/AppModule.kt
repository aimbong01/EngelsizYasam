package com.engelsizyasam.di

import android.content.Context
import androidx.room.Room
import com.engelsizyasam.common.Constants
import com.engelsizyasam.data.local.BookDatabase
import com.engelsizyasam.data.local.BookDao
import com.engelsizyasam.data.remote.NewsApi
import com.engelsizyasam.data.remote.SeriesApi
import com.engelsizyasam.data.remote.SeriesDetailApi
import com.engelsizyasam.data.repository.NewsRepositoryImp
import com.engelsizyasam.data.repository.SeriesDetailRepositoryImp
import com.engelsizyasam.data.repository.SeriesRepositoryImp
import com.engelsizyasam.domain.repository.NewsRepository
import com.engelsizyasam.domain.repository.SeriesDetailRepository
import com.engelsizyasam.domain.repository.SeriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSeriesApi(client: OkHttpClient): SeriesApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL2)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SeriesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSeriesDetailApi(client: OkHttpClient): SeriesDetailApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL2)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SeriesDetailApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsApi(client: OkHttpClient): NewsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideSeriesRepository(api: SeriesApi): SeriesRepository {
        return SeriesRepositoryImp(api)
    }

    @Provides
    @Singleton
    fun provideSeriesDetailRepository(api: SeriesDetailApi): SeriesDetailRepository {
        return SeriesDetailRepositoryImp(api)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsApi): NewsRepository {
        return NewsRepositoryImp(api)
    }


}


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): BookDatabase {
        return Room.databaseBuilder(appContext, BookDatabase::class.java, "book_database").build()
    }

    @Provides
    fun provideBookDatabaseDao(database: BookDatabase): BookDao {
        return database.bookDao
    }
}