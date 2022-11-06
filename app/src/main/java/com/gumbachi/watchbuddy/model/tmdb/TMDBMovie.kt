package com.gumbachi.watchbuddy.model.tmdb

import com.gumbachi.watchbuddy.model.enums.ReleaseStatus
import com.gumbachi.watchbuddy.model.enums.SourceAPI
import com.gumbachi.watchbuddy.model.enums.WatchStatus
import com.gumbachi.watchbuddy.model.interfaces.Movie
import java.time.LocalDate
import kotlin.random.Random

data class TMDBMovie(
    override val id: Int,
    val backdropURL: String,
    override val posterURL: String,
    val budget: Int,
    val revenue: Int,
    override val title: String,
    val overview: String,
    override val releaseDate: LocalDate,
    override val runtime: String,
    override val releaseStatus: ReleaseStatus,
    val tagline: String,
    val globalScore: Double,

    override var userScore: Double = 0.0,
    override var userNotes: String = "",
    override var watchStatus: WatchStatus = WatchStatus.Watching,

    override val api: SourceAPI = SourceAPI.TMDB,

    override var startDate: LocalDate? = null,
    override var finishDate: LocalDate? = null,
): Movie {
    companion object {
//        fun fromDetails(
//            details: TMDBMovieDetailsDTO,
//            score: Double,
//            notes: String,
//        ): TMDBMovie {
//            return TMDBMovie(
//                id = details.id,
//                backdropURL = details.backdropURL(),
//                posterURL = details.posterURL(),
//                budget = details.budget,
//                revenue = details.revenue,
//                title = details.title,
//                overview = details.getOverview(),
//                runtime = details.getRuntime(),
//                tagline = details.tagline ?: "",
//                globalScore = details.globalScore,
//                releaseDate = details.releaseDate(),
//                releaseStatus = when (details.status) {
//                    "Rumored" -> ReleaseStatus.Unreleased("Rumored")
//                    "Canceled" -> ReleaseStatus.Unreleased("Canceled")
//                    "Released" -> ReleaseStatus.Released("Released")
//                    else -> ReleaseStatus.Unreleased("Planned")
//                },
//                userScore = score,
//                userNotes = notes
//            )
//        }

        fun createDummy(): TMDBMovie {
            return TMDBMovie(
                id = 550,
                title = listOf("Movie Title Goes Here and it is very very very long", "Short Title").random(),
                overview = "Movie Description Goes Here",
                posterURL = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/fyy1nDC8wm553FCiBDojkJmKLCs.jpg",
                globalScore = Random.nextDouble(until = 10.1),
                userScore = Random.nextDouble(until = 10.1),
                runtime = "1h 45m",
                releaseDate = LocalDate.of(
                    Random.nextInt(2000, 2025),
                    Random.nextInt(1,13),
                    Random.nextInt(1, 29)
                ),
                budget = 123,
                revenue = 123,
                backdropURL = "",
                tagline = "Tagline Goes Here",
                releaseStatus = ReleaseStatus.random(),
                watchStatus = WatchStatus.random()
            )
        }
    }
}
