package com.gumbachi.watchbuddy.model.enums.data

// TODO May need to switch to sealed class
enum class MediaType(subtype: String) {
    Movie("Movie"),
    Show("Show"),
}