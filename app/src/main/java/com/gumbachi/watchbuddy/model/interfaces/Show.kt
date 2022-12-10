package com.gumbachi.watchbuddy.model.interfaces

interface Show: Media {
    val totalEpisodes: Int
    val episodesWatched: Int

    override fun clone(): Show
}