package com.gumbachi.watchbuddy.model.interfaces

import io.realm.kotlin.types.RealmObject

interface RealmSavable {
    fun toRealmObject(): RealmObject
}

