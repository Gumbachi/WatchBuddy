package com.gumbachi.watchbuddy.data.local.realm

import android.util.Log
import com.gumbachi.watchbuddy.data.local.realm.objects.RealmMovie
import com.gumbachi.watchbuddy.data.local.realm.objects.RealmUserSettings
import com.gumbachi.watchbuddy.model.UserSettings
import com.gumbachi.watchbuddy.model.interfaces.Movie
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

private const val TAG = "Database"

interface WatchbuddyDatabase {

    // Settings Operations
    suspend fun getUserSettingsFlow(): Flow<UserSettings>
    suspend fun updateUserSettings(settings: UserSettings)

    // Movie Operations
    suspend fun getMoviesFlow(): Flow<List<Movie>>
    suspend fun writeMovie(movie: Movie)
    suspend fun eraseMovie(movie: Movie)
    suspend fun updateMovie(old: Movie, new: Movie)
}

class WatchbuddyDB @Inject constructor() : WatchbuddyDatabase {

    init {
        Log.d(TAG, "WatchbuddyDB Created")
    }

    private val realmConfig = RealmConfiguration
        .Builder(schema = setOf(RealmUserSettings::class, RealmMovie::class))
        .schemaVersion(2)
        .build()

    private val realm: Realm = Realm.open(realmConfig)

    // Settings Related Operations

    override suspend fun getUserSettingsFlow(): Flow<UserSettings> {
        val settingsFlow = realm.query<RealmUserSettings>().first().asFlow()
        return settingsFlow.mapNotNull {
            it.obj?.toUserSettings()
        }
    }

    override suspend fun updateUserSettings(settings: UserSettings) {
        realm.write {
            query<RealmUserSettings>().first().find()?.let {
                findLatest(it)?.apply {
                    cardStyle = settings.cardStyle.toString()
                    scoreFormat = settings.scoreFormat.toString()
                    movieSort = settings.movieSort.toString()
                    showSort = settings.showSort.toString()
                }
            }
            Log.d(TAG, "Settings Write Performed")
        }
    }

    // Movie Related Operations

    override suspend fun getMoviesFlow(): Flow<List<Movie>> {
        val movies = realm.query<RealmMovie>().asFlow()

        Log.d(TAG, "Getting Movies Flow")

        return movies.mapNotNull { changes ->
            changes.list.map { it.toMovie() }
        }
    }

    override suspend fun writeMovie(movie: Movie) {
        realm.write {
            copyToRealm(RealmMovie(movie))
            Log.d(TAG, "Movie Write Performed")
        }
    }

    override suspend fun eraseMovie(movie: Movie) {
        realm.write {
            delete(query<RealmMovie>("id == '${movie.qualifiedID}'").find())
            Log.d(TAG, "Movie Delete Performed")
        }
    }

    override suspend fun updateMovie(old: Movie, new: Movie) {
        realm.write {
            realm.query<RealmMovie>("id == '${old.qualifiedID}'").first().find()?.let { realmMovie ->
                findLatest(realmMovie)?.let {
                    it.userScore = new.userScore
                    it.userNotes = new.userNotes
                    it.watchStatus = new.watchStatus.toString()
                    it.startDate = new.startDate?.toEpochDay()
                    it.finishDate = new.finishDate?.toEpochDay()
                    it.lastUpdate = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
                }
            }
            Log.d(TAG, "Movie Update Performed")
        }
    }
}