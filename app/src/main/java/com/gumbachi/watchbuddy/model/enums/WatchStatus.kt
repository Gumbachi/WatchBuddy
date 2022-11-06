package com.gumbachi.watchbuddy.model.enums

enum class WatchStatus {
    Watching,
    Planning,
    Completed,
    Dropped,
    Repeating;

    companion object {
        fun random() = WatchStatus.values().random()
        fun fromIndex(index: Int) = WatchStatus.values()[index]
    }
}
