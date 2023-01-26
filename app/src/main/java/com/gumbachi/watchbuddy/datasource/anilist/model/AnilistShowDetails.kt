package com.gumbachi.watchbuddy.datasource.anilist.model

import com.gumbachi.watchbuddy.model.interfaces.MediaDetails

data class AnilistShowDetails(
    override val id: Int,
    override val title: String,
    override val posterURL: String,
    val backdropURL: String,
    val overview: String?
) : MediaDetails {
    fun shortDetails() = listOf(
        "Anilist ID: $id"
    )
}
