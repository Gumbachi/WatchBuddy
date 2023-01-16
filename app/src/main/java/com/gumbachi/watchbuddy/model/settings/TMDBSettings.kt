package com.gumbachi.watchbuddy.model.settings

import io.realm.kotlin.types.EmbeddedRealmObject

data class TMDBSettings(
    val enabled: Boolean = true,
    val adult: Boolean = false
) {

    fun toRealmTMDBSettings() = RealmTMDBSettings(
        enabled = enabled,
        adult = adult
    )

}

class RealmTMDBSettings(
    private var enabled: Boolean = true,
    private var adult: Boolean = false
) : EmbeddedRealmObject {

    constructor(): this(enabled = true)

    fun toTMDBSettings() = TMDBSettings(
        enabled = enabled,
        adult = adult
    )

}


