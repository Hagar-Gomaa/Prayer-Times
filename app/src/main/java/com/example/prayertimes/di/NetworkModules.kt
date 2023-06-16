package com.example.prayertimes.di

import com.example.prayertimes.data.remote.service.PrayerTimesService
import com.example.prayertimes.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModules {
        @Provides
        @Singleton
        fun providePrayerTimesService(retrofit: Retrofit): PrayerTimesService {
            return retrofit.create(PrayerTimesService::class.java)
        }

        @Provides
        @Singleton
        fun provideRetrofit(
            client: OkHttpClient,
            gsonConverter: GsonConverterFactory
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(gsonConverter)
                .client(client)
                .build()
        }

        @Provides
        @Singleton
        fun provideClient(
            loggingInterceptor: HttpLoggingInterceptor
        ): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        }

        @Provides
        @Singleton
        fun provideLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        }

        @Provides
        @Singleton
        fun provideGsonConverterFactory(): GsonConverterFactory {
            return GsonConverterFactory.create()
        }

}