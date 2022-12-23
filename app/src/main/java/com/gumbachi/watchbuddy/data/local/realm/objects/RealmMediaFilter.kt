package com.gumbachi.watchbuddy.data.local.realm.objects

import io.realm.kotlin.ext.realmSetOf
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmMediaFilter() : RealmObject {
    @PrimaryKey
    var id = 0

    var allowedApis = realmSetOf<String>()
    var allowedMediaTypes = realmSetOf<String>()

}