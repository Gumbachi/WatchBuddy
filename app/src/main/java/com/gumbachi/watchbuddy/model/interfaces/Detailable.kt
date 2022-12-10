package com.gumbachi.watchbuddy.model.interfaces

interface Detailable {
    val id: Int
    val title: String
    val posterURL: String
    val backdropURL: String
    val overview: String?

    fun shortDetails(): List<String>
}
