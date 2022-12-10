package com.gumbachi.watchbuddy.model.enums.data

enum class WatchStatus {
    Watching,
    Planning,
    Completed,
    Dropped,
    Repeating;

    companion object {
        fun random(): WatchStatus = values().random()
    }
}
