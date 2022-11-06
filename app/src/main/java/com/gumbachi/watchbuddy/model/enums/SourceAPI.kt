package com.gumbachi.watchbuddy.model.enums

import androidx.annotation.DrawableRes
import com.gumbachi.watchbuddy.R

sealed class SourceAPI(val name: String, @DrawableRes val logo:  Int) {
    object TMDB: SourceAPI("TMDB", R.drawable.tmdb)
    object Anilist: SourceAPI("AniList", R.drawable.anilist)
    object Unknown: SourceAPI("Unknown", R.drawable.unknown_api)

    override fun toString(): String {
        return this.name
    }

    companion object {
        fun fromString(apiString: String): SourceAPI {
            return when (apiString) {
                "TMDB" -> SourceAPI.TMDB
                "Anilist" -> SourceAPI.Anilist
                else -> SourceAPI.Unknown
            }
        }
    }
}