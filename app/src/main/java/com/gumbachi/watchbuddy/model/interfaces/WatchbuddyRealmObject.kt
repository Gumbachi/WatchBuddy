package com.gumbachi.watchbuddy.model.interfaces

interface WatchbuddyRealmObject {
    fun toWatchbuddyObject(): RealmSavable
}