package com.gumbachi.watchbuddy.model.settings

import com.gumbachi.watchbuddy.model.enums.configuration.AnilistTitleLanguage
import io.realm.kotlin.types.EmbeddedRealmObject

data class AnilistSettings(
    val enabled: Boolean = true,
    val preferredLanguage: AnilistTitleLanguage = AnilistTitleLanguage.English,
    val adult: Boolean = false,
) {

    fun toRealmAnilistSettings() = RealmAnilistSettings(
        enabled = enabled,
        preferredLanguage = preferredLanguage.name,
        adult = adult
    )
}


class RealmAnilistSettings(
    private var enabled: Boolean = true,
    private var preferredLanguage: String = AnilistTitleLanguage.English.name,
    private var adult: Boolean = false
) : EmbeddedRealmObject {

    constructor(): this(enabled = true)

    fun toAnilistSettings() = AnilistSettings(
        enabled = enabled,
        preferredLanguage = AnilistTitleLanguage.valueOf(preferredLanguage),
        adult = adult
    )
}