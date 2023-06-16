package com.example.prayertimes.di

import com.example.prayertimes.Mapper
import com.example.prayertimes.data.remote.mapper.MethodsDtoToEntityMapper
import com.example.prayertimes.data.remote.service.PrayerTimesService
import com.example.prayertimes.data.repository.RepositoryImp
import com.example.prayertimes.domain.Repository
import com.example.prayertimes.domain.usecase.GetMethodsUseCase
import com.example.prayertimes.ui.mapper.MethodsEntityToUiMapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
  @ViewModelScoped
  abstract fun providesRepository(repositoryImp: RepositoryImp): Repository


}




