package com.example.peopleandrooms.di

import android.app.Application
import com.example.peopleandrooms.data.api.ApiDetails
import com.example.peopleandrooms.data.api.ApiService
import com.example.peopleandrooms.data.repo.Repository
import com.example.peopleandrooms.data.repo.RepositoryImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
@HiltAndroidApp
class AppDependency : Application() {

    @Provides
    fun getGson(): Gson {
        return Gson()
    }
    @Provides
    fun provideConverter(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }
    @Provides
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()

    }
    @Provides
    fun provideRetrofit(
        converter: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().baseUrl(ApiDetails.BASE_URL)
            .addConverterFactory(converter)
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideRoomService( retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun providesRepository(apiService: ApiService): Repository {
        return RepositoryImpl(apiService)
    }

}
