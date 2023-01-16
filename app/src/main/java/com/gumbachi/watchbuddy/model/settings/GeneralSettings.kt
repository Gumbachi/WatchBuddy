package com.gumbachi.watchbuddy.model.settings

import com.gumbachi.watchbuddy.ui.navigation.WatchbuddyMainDestination
import io.realm.kotlin.types.EmbeddedRealmObject

data class GeneralSettings(
    val startingDestination: WatchbuddyMainDestination = WatchbuddyMainDestination.Movies,
) {

    fun toRealmGeneralSettings() = RealmGeneralSettings(
        startingDestination = startingDestination.name
    )
}

class RealmGeneralSettings(
    private var startingDestination: String = WatchbuddyMainDestination.Movies.name,
) : EmbeddedRealmObject {

    constructor(): this(startingDestination = WatchbuddyMainDestination.Movies.name)

    fun toGeneralSettings() = GeneralSettings(
        startingDestination = WatchbuddyMainDestination.valueOf(startingDestination)
    )
}