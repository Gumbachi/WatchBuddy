package com.gumbachi.watchbuddy.data.remote.anilist.mappers

import android.util.Log
import com.gumbachi.watchbuddy.model.enums.data.MediaType
import com.gumbachi.watchbuddy.model.enums.data.ReleaseStatus
import com.gumbachi.watchbuddy.type.MediaFormat
import com.gumbachi.watchbuddy.type.MediaStatus

private const val TAG = "AnilistConverters"

fun MediaStatus?.toReleaseStatus(type: MediaType): ReleaseStatus {
    return when (this) {
        MediaStatus.FINISHED -> {
            if (type == MediaType.Show)
                ReleaseStatus.Finished
            else
                ReleaseStatus.Released
        }

        MediaStatus.RELEASING -> ReleaseStatus.Releasing
        MediaStatus.NOT_YET_RELEASED -> ReleaseStatus.Unreleased
        MediaStatus.CANCELLED -> ReleaseStatus.Cancelled
        else -> ReleaseStatus.Unknown
    }
}

fun MediaFormat?.toMediaType(): MediaType {
    return when (this) {
        MediaFormat.TV,
        MediaFormat.TV_SHORT,
        MediaFormat.ONA,
        MediaFormat.OVA,
        MediaFormat.SPECIAL -> MediaType.Show
        MediaFormat.MOVIE -> MediaType.Movie
        else -> {
            Log.e(TAG, "Couldn't validate Media Type of $this")
            throw Exception("Couldn't parse media type")
        }
    }
}
