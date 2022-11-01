package com.gumbachi.watchbuddy.data.remote.tmdb.models.movie

//data class TMDBMovie(
//    val id: Int,
//    val backdropURL: String,
//    val posterURL: String,
//    val budget: Int,
//    val revenue: Int,
//    val title: String,
//    val overview: String,
//    val releaseDate: LocalDate,
//    val runtime: Int,
//    val releaseStatus: ReleaseStatus,
//    val tagline: String,
//    val globalScore: Double,
//
//    var userScore: Double = 0.0,
//    override var watchStatus: WatchStatus,
//    var userNotes: String = ""
//): Movie {
//    companion object {
//        fun fromDetails(
//            details: TMDBMovieDetails,
//            score: Double,
//            watchStatus: WatchStatus,
//            notes: String,
//        ): TMDBMovie {
//            return TMDBMovie(
//                id = details.id,
//                backdropURL = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2${details.backdrop_path}",
//                posterURL = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2${details.poster_path}",
//                budget = details.budget,
//                revenue = details.revenue,
//                title = details.title,
//                overview = details.overview ?: "",
//                runtime = details.runtime ?: 0,
//                tagline = details.tagline ?: "",
//                globalScore = details.vote_average,
//                releaseDate = LocalDate.parse(details.release_date),
//                releaseStatus = when (details.status) {
//                    "Rumored" -> ReleaseStatus.Unreleased("Rumored")
//                    "Canceled" -> ReleaseStatus.Unreleased("Canceled")
//                    "Released" -> ReleaseStatus.Released("Released")
//                    else -> ReleaseStatus.Unreleased("Planned")
//                },
//                watchStatus = watchStatus,
//                userScore = score,
//                userNotes = notes
//            )
//        }
//
//        private fun createDummy(): TMDBMovie {
//            return TMDBMovie(
//                id = Random.nextInt(),
//                title = listOf("Movie Title Goes Here and it is very very very long", "Short Title").random(),
//                overview = "Movie Description Goes Here",
//                posterURL = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/fyy1nDC8wm553FCiBDojkJmKLCs.jpg",
//                globalScore = Random.nextDouble(until = 10.1),
//                userScore = Random.nextDouble(until = 10.1),
//                watchStatus = WatchStatus.values().random(),
//                runtime = Random.nextInt(3600, 10800),
//                releaseDate = LocalDate.of(
//                    Random.nextInt(2000, 2025),
//                    Random.nextInt(1,13),
//                    Random.nextInt(1, 29)
//                ),
//                budget = 123,
//                revenue = 123,
//                backdropURL = "",
//                tagline = "",
//                releaseStatus = listOf(ReleaseStatus.Unreleased(), ReleaseStatus.Released()).random()
//            )
//        }
//
//        fun createDummies(amount: Int): List<TMDBMovie> {
//            return List(amount) { createDummy() }
//        }
//    }
//
//    private fun formattedRuntime(longFormat: Boolean = false): String {
//        val hours = runtime/3600
//        val minutes = (runtime % 3600) / 60
//        return when (longFormat) {
//            true -> {
//                when (hours) {
//                    0 -> if (minutes == 1) "1 minute" else "$minutes minutes"
//                    1 -> if (minutes == 1) "$1 hour 1 minute" else "1 hour $minutes minutes"
//                    else -> if (minutes == 1) "$hours hours 1 minute" else "$hours hours $minutes minutes"
//                }
//            }
//            false -> if (hours == 0) "${minutes}m" else "${hours}h ${minutes}m"
//        }
//    }
//
//    @Composable
//    override fun Card(
//        modifier: Modifier,
//        onClick: () -> Unit
//    ) {
//        MediaCard(
//            imageURL = posterURL,
//            headline = title,
//            primarySubtitle = formattedRuntime(),
//            secondarySubtitle = releaseDate.toString(),
//            score = String.format("%.1f", userScore),
//            status = releaseStatus,
//            modifier = modifier.clickable {
//                onClick()
//            }
//        )
//    }
//
//    @Composable
//    override fun CompactCard(
//        modifier: Modifier,
//        onClick: () -> Unit
//    ) {
//        CompactMediaCard(
//            imageURL = posterURL,
//            headline = title,
//            primarySubtitle = formattedRuntime(),
//            secondarySubtitle = releaseDate.toString(),
//            score = String.format("%.1f", userScore),
//            status = releaseStatus,
//            modifier = modifier.clickable {
//                onClick()
//            }
//        )
//    }
//
//
//}
