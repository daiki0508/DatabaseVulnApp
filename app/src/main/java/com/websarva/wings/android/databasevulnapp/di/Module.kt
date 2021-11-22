package com.websarva.wings.android.databasevulnapp.di

import com.websarva.wings.android.databasevulnapp.repository.SharedPreferenceRepository
import com.websarva.wings.android.databasevulnapp.repository.SharedPreferenceRepositoryClient
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class Module {
    @Singleton
    @Binds
    abstract fun bindSharedPreferenceRepository(preferenceRepositoryClient: SharedPreferenceRepositoryClient): SharedPreferenceRepository
}