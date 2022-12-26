package com.gumbachi.watchbuddy.model.api.anilist

import com.gumbachi.watchbuddy.model.interfaces.Detailable

data class AnilistMovieDetails(
    override val id: Int,
    override val title: String,
    override val posterURL: String,
    override val backdropURL: String,
    override val overview: String?
) : Detailable {

    override fun shortDetails() = listOf(
        "Anilist ID: $id"
    )
}
