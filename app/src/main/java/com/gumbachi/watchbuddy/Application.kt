package com.gumbachi.watchbuddy

import android.app.Application
import android.util.Log
import com.gumbachi.watchbuddy.database.WatchbuddyDB
import com.gumbachi.watchbuddy.database.WatchbuddyDatabase
import com.gumbachi.watchbuddy.datasource.anilist.api.AnilistAPI
import com.gumbachi.watchbuddy.datasource.tmdb.api.TMDBApi
import com.gumbachi.watchbuddy.module.details.DetailsRepository
import com.gumbachi.watchbuddy.module.details.DetailsRepositoryImpl
import com.gumbachi.watchbuddy.module.details.DetailsViewModel
import com.gumbachi.watchbuddy.module.movies.MoviesRepository
import com.gumbachi.watchbuddy.module.movies.MoviesRepositoryImpl
import com.gumbachi.watchbuddy.module.movies.MoviesViewModel
import com.gumbachi.watchbuddy.module.search.SearchRepository
import com.gumbachi.watchbuddy.module.search.SearchRepositoryImpl
import com.gumbachi.watchbuddy.module.search.home.SearchHomeViewModel
import com.gumbachi.watchbuddy.module.search.mediasearch.MediaSearchViewModel
import com.gumbachi.watchbuddy.module.settings.SettingsRepository
import com.gumbachi.watchbuddy.module.settings.SettingsRepositoryImpl
import com.gumbachi.watchbuddy.module.settings.SettingsViewModel
import com.gumbachi.watchbuddy.module.shows.ShowsRepository
import com.gumbachi.watchbuddy.module.shows.ShowsRepositoryImpl
import com.gumbachi.watchbuddy.module.shows.ShowsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    // Universal
    singleOf(::WatchbuddyDB) { bind<WatchbuddyDatabase>() }
    singleOf(::TMDBApi) { bind() }
    singleOf(::AnilistAPI) { bind() }

    // Repositories
    singleOf(::MoviesRepositoryImpl) { bind<MoviesRepository>() }
    singleOf(::ShowsRepositoryImpl) { bind<ShowsRepository>() }
    singleOf(::SearchRepositoryImpl) { bind<SearchRepository>() }
    singleOf(::SettingsRepositoryImpl) { bind<SettingsRepository>() }
    singleOf(::DetailsRepositoryImpl) { bind<DetailsRepository>() }

    // ViewModels
    viewModelOf(::MoviesViewModel)
    viewModelOf(::ShowsViewModel)
    viewModelOf(::SearchHomeViewModel)
    viewModelOf(::MediaSearchViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::DetailsViewModel)
}

class WatchbuddyApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        Log.d("Application", "Starting KOIN")

        startKoin {
            androidLogger()
            androidContext(this@WatchbuddyApplication)
            modules(appModule)
        }
    }
}