package com.gumbachi.watchbuddy.model.enums.configuration

enum class Sort(val label: String) {
    ScoreDescending("Score: Descending"),
    ScoreAscending("Score: Ascending"),

    ReleaseDateAscending("Release Date: Oldest"),
    ReleaseDateDescending("Release Date: Newest"),
    Alphabetical("Alphabetical"),

    ReleaseStatus("Release Status"),
    RecentlyUpdated("Recently Updated")
}