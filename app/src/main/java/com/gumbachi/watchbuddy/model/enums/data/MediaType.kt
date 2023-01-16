package com.gumbachi.watchbuddy.model.enums.data

enum class MediaType(val subtype: String, val isEpisodic: Boolean = true) {
    Movie("Movie", isEpisodic = false),
    Show("Show"),
}