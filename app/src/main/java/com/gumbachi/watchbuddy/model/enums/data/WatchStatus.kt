package com.gumbachi.watchbuddy.model.enums.data

enum class WatchStatus {
    Watching,
    Planning,
    Completed,
    Dropped,
    Repeating;

    fun toIndex(): Int = values().indexOf(this)

    companion object {
        fun random(): WatchStatus = values().random()
        fun fromIndex(index: Int): WatchStatus = values()[index]
    }
}
