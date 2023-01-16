package com.gumbachi.watchbuddy.database

import android.util.Log
import com.gumbachi.watchbuddy.database.objects.RealmMovie
import com.gumbachi.watchbuddy.database.objects.RealmShow
import com.gumbachi.watchbuddy.model.WatchBuddyID
import com.gumbachi.watchbuddy.model.interfaces.Media
import com.gumbachi.watchbuddy.model.interfaces.Movie
import com.gumbachi.watchbuddy.model.interfaces.Show
import com.gumbachi.watchbuddy.model.settings.*
import com.gumbachi.watchbuddy.model.toWatchbuddyID
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val TAG = "Database"

interface WatchbuddyDatabase {

    // Settings Operations
    suspend fun getUserSettingsFlow(): Flow<UserSettings>
    suspend fun updateUserSettingsTo(settings: UserSettings)

    // Movie Operations
    suspend fun getMoviesFlow(): Flow<List<Movie>>
    suspend fun addMovie(movie: Movie)
    suspend fun removeMovie(movie: Movie)
    suspend fun updateMovie(updatedMovie: Movie)
    suspend fun findMovieByID(id: WatchBuddyID): Movie?

    // Show Operations
    suspend fun getShowsFlow(): Flow<List<Show>>
    suspend fun addShow(show: Show)
    suspend fun removeShow(show: Show)
    suspend fun updateShow(updatedShow: Show)
    suspend fun findShowByID(id: WatchBuddyID): Show?

    // Media Operations
    suspend fun getSavedMediaIDs(): Flow<List<WatchBuddyID>>
    suspend fun findMedia(id: WatchBuddyID): Media?
    suspend fun containsMedia(id: WatchBuddyID): Boolean

}

class WatchbuddyDB : WatchbuddyDatabase {

    init {
        Log.d(TAG, "WatchbuddyDB Created")
    }

    private val realmConfig = RealmConfiguration
        .Builder(
            schema = setOf(
                RealmUserSettings::class,
                RealmMovie::class,
                RealmShow::class,

                // Embedded Objects
                RealmGeneralSettings::class,
                RealmCardSettings::class,
                RealmMovieSettings::class,
                RealmShowSettings::class,
                RealmAnilistSettings::class,
                RealmTMDBSettings::class
            )
        )
        .deleteRealmIfMigrationNeeded()
        .build()

    private val realm: Realm = Realm.open(realmConfig)


    //region Settings Operations
    override suspend fun getUserSettingsFlow(): Flow<UserSettings> {

        // Insert if not found
        if (realm.query<RealmUserSettings>().first().find() == null) {
            realm.write {
                copyToRealm(
                    instance = RealmUserSettings(),
                    updatePolicy = UpdatePolicy.ALL
                )
            }
            Log.d(TAG, "Default settings not found. Using defaults")
        }

        val settingsFlow = realm.query<RealmUserSettings>().first().asFlow()
        return settingsFlow.map {
            it.obj?.toUserSettings() ?: UserSettings()
        }
    }

    override suspend fun updateUserSettingsTo(settings: UserSettings) {
        realm.write {
            copyToRealm(
                instance = settings.toRealmUserSettings(),
                updatePolicy = UpdatePolicy.ALL
            )
        }
        Log.d(TAG, "Settings Update Performed")
    }
    //endregion

    //region Movie Operations
    override suspend fun getMoviesFlow(): Flow<List<Movie>> {
        Log.d(TAG, "Getting Movies Flow")
        val movies = realm.query<RealmMovie>().asFlow()
        return movies.map { changes ->
            changes.list.map { it.toMovie() }
        }
    }

    override suspend fun addMovie(movie: Movie) {
        realm.write {
            copyToRealm(movie.toRealmMovie())
        }
        Log.d(TAG, "Movie Write Performed")
    }

    override suspend fun removeMovie(movie: Movie) {
        realm.write {
            delete(query<RealmMovie>("id == $0", "${movie.watchbuddyID}").find())
        }
        Log.d(TAG, "Movie Delete Performed")
    }

    override suspend fun updateMovie(updatedMovie: Movie) {
        realm.write {
            copyToRealm(
                instance = updatedMovie.toRealmMovie(),
                updatePolicy = UpdatePolicy.ALL
            )
        }
        Log.d(TAG, "Movie Update Performed")
    }

    override suspend fun findMovieByID(id: WatchBuddyID): Movie? {
        return realm.query<RealmMovie>("id == $0", "$id").first().find()?.toMovie()
    }
    //endregion

    //region Show Operations
    override suspend fun getShowsFlow(): Flow<List<Show>> {
        Log.d(TAG, "Getting Shows Flow")
        val shows = realm.query<RealmShow>().asFlow()
        return shows.map { changes ->
            changes.list.map { it.toShow() }
        }
    }

    override suspend fun addShow(show: Show) {
        realm.write {
            copyToRealm(show.toRealmShow())
        }
        Log.d(TAG, "Show Write Performed")
    }

    override suspend fun removeShow(show: Show) {
        realm.write {
            delete(query<RealmShow>("id == $0", "${show.watchbuddyID}").find())
        }
        Log.d(TAG, "Show Delete Performed")
    }

    override suspend fun updateShow(updatedShow: Show) {
        realm.write {
            copyToRealm(
                instance = updatedShow.toRealmShow(),
                updatePolicy = UpdatePolicy.ALL
            )
        }
    }

    override suspend fun findShowByID(id: WatchBuddyID): Show? {
        return realm.query<RealmShow>("id == $0", "$id").first().find()?.toShow()
    }
    //endregion

    //region Media Operations
    override suspend fun getSavedMediaIDs(): Flow<List<WatchBuddyID>> {
        val savedMovieIDs = realm.query<RealmMovie>().asFlow().map { changes ->
            changes.list.map { it.id.toWatchbuddyID() }
        }
        val savedShowIDs = realm.query<RealmShow>().asFlow().map { changes ->
            changes.list.map { it.id.toWatchbuddyID() }
        }

        // Merge IDs into one flow
        return savedMovieIDs.combine(savedShowIDs) { movieIDs, showIDs -> movieIDs + showIDs }
    }

    override suspend fun findMedia(id: WatchBuddyID): Media? =
        findMovieByID(id) ?: findShowByID(id)

    override suspend fun containsMedia(id: WatchBuddyID): Boolean =
        getSavedMediaIDs().first().contains(id)

    //endregion
}