package com.gumbachi.watchbuddy.model.interfaces

interface Detailable {
    val id: Int
    val title: String
    val posterURL: String
    val backdropURL: String
    val overview: String?

    fun shortDetails(): List<String>

    data class Dummy(
        override val id: Int = 550,
        override val title: String = "Details Title Goes Here",
        override val posterURL: String = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
        override val backdropURL: String = "",
        override val overview: String? = "This is a movie/show about something yada yada"

    ) : Detailable {
        override fun shortDetails() = List(15) { "Howdy: Howdy Howdy / Howdy" }
    }
}


