package com.engelsizyasam.di

import android.content.Context
import androidx.room.Room
import com.engelsizyasam.BaseApplication
import com.engelsizyasam.database.BookDatabase
import com.engelsizyasam.database.BookDatabaseDao
import com.engelsizyasam.network.NewsApiService
import com.engelsizyasam.network.SeriesApiService
import com.engelsizyasam.network.SeriesDetailApiService
import com.engelsizyasam.view.BookViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }


}

@Module
@InstallIn(SingletonComponent::class)
object NewsApiModule {

    @Provides
    @Named("news_base")
    fun provideNewsUrl() = "https://newsapi.org/v2/"

    @Provides
    @Singleton
    @Named("news_retrofit")
    fun provideRetrofit(@Named("news_base") BASE_URL: String): Retrofit = Retrofit.Builder()
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
    fun provideNewsApiService(@Named("news_retrofit") retrofit: Retrofit) = retrofit.create(NewsApiService::class.java)


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