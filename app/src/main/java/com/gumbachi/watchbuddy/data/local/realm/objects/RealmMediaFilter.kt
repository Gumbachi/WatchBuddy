package com.gumbachi.watchbuddy.data.local.realm.objects

import com.gumbachi.watchbuddy.model.MediaFilter
import com.gumbachi.watchbuddy.model.enums.data.API
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import io.realm.kotlin.ext.realmSetOf
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmMediaFilter() : RealmObject {
    @PrimaryKey
    var id = 0

    var allowedApis = realmSetOf<String>()
    var allowedMediaTypes = realmSetOf<String>()


    //region Converters
    fun toMediaFilter(): MediaFilter =
        MediaFilter(
            allowedAPIs = allowedApis.map { API.valueOf(it) }.toSet(),
            allowedMediaTypes = allowedMediaTypes.map { MediaType.valueOf(it) }.toSet()
        )
    //endregion
}