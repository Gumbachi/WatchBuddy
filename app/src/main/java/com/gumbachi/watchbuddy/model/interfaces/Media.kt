package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.model.enums.data.Source

interface Media: Editable, Sortable {
    val source: Source
    val posterURL: String
    
    val qualifiedID: String
        get() = "$source|$id"
}