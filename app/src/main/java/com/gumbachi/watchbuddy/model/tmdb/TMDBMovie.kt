package com.gumbachi.watchbuddy.model.tmdb

import com.gumbachi.watchbuddy.model.EditableState
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.model.enums.data.Source
import com.gumbachi.watchbuddy.model.enums.data.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Movie
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.random.Random

data class TMDBMovie(
    override val id: Int,
    override val posterURL: String,
    override val title: String,
    override val releaseDate: LocalDate,
    override val runtime: String,
    override val releaseStatus: ReleaseStatus,

    override var userScore: Int = 0,
    override var userNotes: String = "",
    override var watchStatus: WatchStatus = WatchStatus.Watching,

    override var startDate: LocalDate? = null,
    override var finishDate: LocalDate? = null,

    override var lastUpdate: LocalDateTime? = null
) : Movie {

    override val source = Source.TMDBMovie

    override infix fun with(edits: EditableState) = this.copy(
        watchStatus = edits.watchStatus,
        userScore = edits.userScore,
        userNotes = edits.userNotes,
        startDate = edits.startDate,
        finishDate = edits.finishDate,
        lastUpdate = LocalDateTime.now()
    )

    companion object {

        fun createDummy(id: Int): TMDBMovie {
            return TMDBMovie(
                id = id,
                title = listOf(
                    "Movie Title Goes Here and it is very very very long",
                    "Short Title"
                ).random(),
                posterURL = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/fyy1nDC8wm553FCiBDojkJmKLCs.jpg",
                userScore = Random.nextInt(until = 101),
                runtime = "1h 45m",
                releaseDate = LocalDate.of(
                    Random.nextInt(2000, 2025),
                    Random.nextInt(1, 13),
                    Random.nextInt(1, 29)
                ),
                releaseStatus = ReleaseStatus.random(),
                watchStatus = WatchStatus.random()
            )
        }
    }

    override fun copy(): TMDBMovie {
        return copy(id = this.id)
    }


}
