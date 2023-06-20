package com.example.prayertimes.di

import com.example.prayertimes.data.local.PrayTimesDataBase
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): PrayTimesDataBase {
        return Room.databaseBuilder(
            context,
            PrayTimesDataBase::class.java,
            "com.example.prayertimes.data.local.PrayTimesDataBase"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun getPrayTimesDao(prayTimesDataBase: PrayTimesDataBase) = prayTimesDataBase.prayTimeDao()
}