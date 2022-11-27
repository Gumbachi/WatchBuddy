package com.gumbachi.watchbuddy.model.enums.data

import androidx.annotation.DrawableRes
import com.gumbachi.watchbuddy.R

enum class API(@DrawableRes val logo:  Int) {
    TMDB(R.drawable.tmdb),
    Anilist(R.drawable.anilist),
    Unknown(R.drawable.unknown_api)
}