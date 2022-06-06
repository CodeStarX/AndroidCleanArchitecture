package mohsen.soltanian.cleanarchitecture.core.data.models.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import mohsen.soltanian.cleanarchitecture.core.data.scopes.moshi.NullToEmptyString

@Parcelize
@JsonClass(generateAdapter = true)
class Movie(
    @Json(name = "adult") val adult: Boolean,
    @NullToEmptyString
    @Json(name = "backdrop_path") val movieBackdrop: String,
    @Json(name = "genre_ids") val genreIds: List<Int>,
    @NullToEmptyString
    @Json(name = "id") val movieId: String,
    @NullToEmptyString
    @Json(name = "original_language") val movieLanguage: String,
    @NullToEmptyString
    @Json(name = "original_title") val originalTitle: String,
    @NullToEmptyString
    @Json(name = "overview") val movieDescription: String,
    @Json(name = "popularity") val popularity: Double,
    @NullToEmptyString
    @Json(name = "poster_path") val moviePoster: String,
    @NullToEmptyString
    @Json(name = "release_date") val movieReleaseDate: String,
    @NullToEmptyString
    @Json(name = "title") val movieTitle: String,
    @Json(name = "video") val video: Boolean,
    @Json(name = "vote_average") val movieVote: Double,
    @Json(name = "vote_count") val voteCount: Long
): Parcelable