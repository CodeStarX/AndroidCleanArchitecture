package mohsen.soltanian.cleanarchitecture.core.data.models.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
class Movie(
    @Json(name = "adult")
    val adult: Boolean?,
    @Json(name = "backdrop_path")
    val movieBackdrop: String?,
    @Json(name = "genre_ids")
    val genreIds: List<Int>?,
    @Json(name = "id")
    val movieId: String?,
    @Json(name = "original_language")
    val movieLanguage: String?,
    @Json(name = "original_title")
    val originalTitle: String?,
    @Json(name = "overview")
    val movieDescription: String?,
    @Json(name = "popularity")
    val popularity: Double?,
    @Json(name = "poster_path")
    val moviePoster: String?,
    @Json(name = "release_date")
    val movieReleaseDate: String?,
    @Json(name = "title")
    val movieTitle: String?,
    @Json(name = "video")
    val video: Boolean?,
    @Json(name = "vote_average")
    val movieVote: Double?,
    @Json(name = "vote_count")
    val voteCount: Long?
) : Parcelable