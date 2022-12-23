package com.gumbachi.watchbuddy.data.local.realm.mappers

import com.gumbachi.watchbuddy.data.local.realm.objects.RealmMediaFilter
import com.gumbachi.watchbuddy.model.MediaFilter
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import io.realm.kotlin.ext.toRealmSet

fun RealmMediaFilter.toMediaFilter(): MediaFilter = MediaFilter(
    allowedAPIs = allowedApis.map { API.valueOf(it) }.toSet(),
    allowedMediaTypes = allowedMediaTypes.map { MediaType.valueOf(it) }.toSet()
)

fun MediaFilter.toRealmMediaFilter(): RealmMediaFilter {
    val filter = this
    return RealmMediaFilter().apply {
        allowedApis = filter.allowedAPIs.map { it.toString() }.toRealmSet()
        allowedMediaTypes = filter.allowedMediaTypes.map { it.toString() }.toRealmSet()
    }
}