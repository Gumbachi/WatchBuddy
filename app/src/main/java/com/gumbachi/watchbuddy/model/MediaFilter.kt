package com.gumbachi.watchbuddy.model

import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.interfaces.Media

data class MediaFilter(
    val allowedAPIs: Set<API> = API.values().toSet(), // allow all by default
    val allowedMediaTypes: Set<MediaType> = MediaType.values().toSet() // allow all by default
) {

    fun includes(mediaType: MediaType) = mediaType in allowedMediaTypes
    fun includes(api: API) = api in allowedAPIs
    fun includes(id: WatchBuddyID) = includes(id.api) && includes(id.type)

    operator fun plus(api: API): MediaFilter = copy(allowedAPIs = allowedAPIs + api)
    operator fun plus(mediaType: MediaType): MediaFilter =
        copy(allowedMediaTypes = allowedMediaTypes + mediaType)

    operator fun minus(api: API): MediaFilter = copy(allowedAPIs = allowedAPIs - api)
    operator fun minus(mediaType: MediaType): MediaFilter =
        copy(allowedMediaTypes = allowedMediaTypes - mediaType)

    val predicate: (Media) -> Boolean = {
        it.watchbuddyID.api in allowedAPIs && it.watchbuddyID.type in allowedMediaTypes
    }
}

