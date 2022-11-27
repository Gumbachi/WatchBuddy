package com.gumbachi.watchbuddy.model.interfaces

import com.gumbachi.watchbuddy.model.EditableState

interface Movie: Sortable, Media {
    val runtime: String

    fun copy(): Movie

    override infix fun with(edits: EditableState): Movie
}