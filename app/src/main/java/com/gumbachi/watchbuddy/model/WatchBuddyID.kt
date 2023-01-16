package com.gumbachi.watchbuddy.model

import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.Source
import kotlinx.serialization.Serializable

@Serializable
data class WatchBuddyID(
    val api: API,
    val type: MediaType,
    val sourceID: Int
) {
    override fun toString() = "$api|$type|$sourceID"
    val source = Source.from(api, type)
}

fun String.toWatchbuddyID(): WatchBuddyID {
    val (api, type, sourceID) = split("|", limit = 3)
    return WatchBuddyID(
        api = API.valueOf(api),
        type = MediaType.valueOf(type),
        sourceID = sourceID.toInt()
    )
}
