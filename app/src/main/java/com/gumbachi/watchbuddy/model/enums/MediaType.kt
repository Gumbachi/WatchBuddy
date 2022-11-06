package com.gumbachi.watchbuddy.model.enums

enum class MediaType {
    Movie, Show;

    companion object {
        fun fromString(type: String): MediaType {
            return when (type) {
                "Movie" -> Movie
                "Show" -> Show
                else -> { throw Exception("Invalid type string") }
            }
        }
    }
}