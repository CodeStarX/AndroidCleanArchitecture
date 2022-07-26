package mohsen.soltanian.cleanarchitecture.core.data.models.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Cast(
    @Json(name= "character")
    val character: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "profile_path")
    val profilePath: String?
)
