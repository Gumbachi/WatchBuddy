package com.gumbachi.watchbuddy.model

import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.Source
import kotlinx.serialization.Serializable

@Serializable
data class WatchbuddyID(
    val api: API,
    val type: MediaType,
    val sourceID: Int
) {
    override fun toString() = "$api|$type|$sourceID"

    val source = Source.from(api, type)

    companion object {
        infix fun from(string: String) = string.split("|", limit = 3).let { (api, type, sourceID) ->
            WatchbuddyID(
                api = API.valueOf(api),
                type = MediaType.valueOf(type),
                sourceID = sourceID.toInt()
            )
        }
    }
}
