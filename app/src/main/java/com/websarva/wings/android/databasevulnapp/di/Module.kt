package com.websarva.wings.android.databasevulnapp.di

import com.websarva.wings.android.databasevulnapp.repository.*
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

    @Singleton
    @Binds
    abstract fun bindDatabaseRepository(databaseRepositoryClient: DatabaseRepositoryClient): DatabaseRepository

    @Singleton
    @Binds
    abstract fun bindRealTimeRepository(realTimeRepositoryClient: RealTimeRepositoryClient): RealtimeRepository

    @Singleton
    @Binds
    abstract fun bindFirebaseStorageRepository(firebaseStorageRepositoryClient: FirebaseStorageRepositoryClient): FirebaseStorageRepository
}