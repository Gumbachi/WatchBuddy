package com.gumbachi.watchbuddy.model.settings

import com.gumbachi.watchbuddy.model.enums.configuration.CardStyle
import com.gumbachi.watchbuddy.model.enums.configuration.ScoreFormat
import io.realm.kotlin.types.EmbeddedRealmObject

data class CardSettings(
    val style: CardStyle = CardStyle.Compact,
    val scoreFormat: ScoreFormat = ScoreFormat.Percentage,
    val showProgressOnPlanned: Boolean = true,
    val showScoreOnPlanned: Boolean = true,
    val showApi: Boolean = true
) {

    fun toRealmCardSettings() = RealmCardSettings(
        style = style.name,
        scoreFormat = scoreFormat.name,
        showProgressOnPlanned = showProgressOnPlanned,
        showScoreOnPlanned = showScoreOnPlanned,
        showApi = showApi
    )

}

class RealmCardSettings(
    private var style: String = CardStyle.Compact.name,
    private var scoreFormat: String = ScoreFormat.Percentage.name,
    private var showProgressOnPlanned: Boolean = true,
    private var showScoreOnPlanned: Boolean = true,
    private var showApi: Boolean = true
) : EmbeddedRealmObject {

    constructor(): this(style = CardStyle.Compact.name)

    fun toCardSettings() = CardSettings(
        style = CardStyle.valueOf(style),
        scoreFormat = ScoreFormat.valueOf(scoreFormat),
        showProgressOnPlanned = showProgressOnPlanned,
        showScoreOnPlanned = showScoreOnPlanned,
        showApi = showApi
    )

}


