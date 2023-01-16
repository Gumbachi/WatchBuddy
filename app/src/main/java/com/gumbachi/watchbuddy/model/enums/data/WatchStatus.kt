package com.gumbachi.watchbuddy.model.enums.data

enum class WatchStatus {
    Watching,
    Planning,
    Completed,
    Dropped,
    Repeating;




    companion object {
        val values = values().toList()
        fun random(): WatchStatus = values().random()
        fun availableOptions(hidden: Set<WatchStatus>) = values().toList() - hidden
    }
}
