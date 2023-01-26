package com.gumbachi.watchbuddy.model.interfaces

@Deprecated("MediaDetails Instead")
interface Detailable {
    val id: Int
    val title: String
    val posterURL: String
    val backdropURL: String
    val overview: String?

    fun shortDetails(): List<String>
}


