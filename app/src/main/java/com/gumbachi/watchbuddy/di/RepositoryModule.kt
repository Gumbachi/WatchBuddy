package com.gumbachi.watchbuddy.di

import com.gumbachi.watchbuddy.data.local.realm.WatchbuddyDB
import com.gumbachi.watchbuddy.data.local.realm.WatchbuddyDatabase
import com.gumbachi.watchbuddy.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMoviesRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository

    @Binds
    @Singleton
    abstract fun bindShowsRepository(
        showRepositoryImpl: ShowRepositoryImpl
    ): ShowRepository

    @Binds
    @Singleton
    abstract fun bindDetailsRepository(
        detailsRepositoryImpl: DetailsRepositoryImpl
    ): DetailsRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        settingsRepositoryImpl: SettingsRepositoryImpl
    ): SettingsRepository

    @Binds
    @Singleton
    abstract fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    @Singleton
    abstract fun bindDB(
        watchbuddyDB: WatchbuddyDB
    ): WatchbuddyDatabase

}