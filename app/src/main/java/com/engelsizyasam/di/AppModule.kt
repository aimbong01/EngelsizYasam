package com.engelsizyasam.di

import android.content.Context
import androidx.room.Room
import com.engelsizyasam.domain.repository.NewsRepository
import com.engelsizyasam.common.Constants
import com.engelsizyasam.data.remote.NewsApi
import com.engelsizyasam.data.repository.NewsRepositoryImp
import com.engelsizyasam.database.BookDatabase
import com.engelsizyasam.database.BookDatabaseDao
import com.engelsizyasam.network.SeriesApiService
import com.engelsizyasam.network.SeriesDetailApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


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
    fun provideNewsRepository(api: NewsApi): NewsRepository {
        return NewsRepositoryImp(api)
    }


}



@Module
@InstallIn(SingletonComponent::class)
object GoogleApiModule {

    @Provides
    @Named("google_base")
    fun provideGoogleUrl() = "https://www.googleapis.com/youtube/v3/"

    @Provides
    @Singleton
    @Named("google_retrofit")
    fun provideRetrofit(@Named("google_base") BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            )
        )
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideSeriesApiService(@Named("google_retrofit") retrofit: Retrofit) = retrofit.create(SeriesApiService::class.java)

    @Provides
    @Singleton
    fun provideSeriesDetailApiService(@Named("google_retrofit") retrofit: Retrofit) = retrofit.create(SeriesDetailApiService::class.java)
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
    fun provideBookDatabaseDao(database: BookDatabase): BookDatabaseDao {
        return database.bookDatabaseDao
    }
}