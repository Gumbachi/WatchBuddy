package com.gumbachi.watchbuddy.model.interfaces

interface Review {
    val avatarURL: String
    val author: String
    val content: String
    val rating: Int?
}