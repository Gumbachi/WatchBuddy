package com.gumbachi.watchbuddy.model.interfaces

interface Movie: Media{
    val runtime: String

    override fun clone(): Movie
}