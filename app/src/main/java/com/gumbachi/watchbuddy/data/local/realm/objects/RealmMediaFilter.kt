package com.gumbachi.watchbuddy.data.local.realm.objects

import com.gumbachi.watchbuddy.model.MediaFilter
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.interfaces.WatchbuddyRealmObject
import io.realm.kotlin.ext.realmSetOf
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmMediaFilter() : RealmObject, WatchbuddyRealmObject {
    @PrimaryKey
    var id = 0

    var allowedApis = realmSetOf<String>()
    var allowedMediaTypes = realmSetOf<String>()


    override fun toWatchbuddyObject() = MediaFilter(
        allowedAPIs = allowedApis.map { API.valueOf(it) }.toSet(),
        allowedMediaTypes = allowedMediaTypes.map { MediaType.valueOf(it) }.toSet()
    )

}