package mohsen.soltanian.cleanarchitecture.core.data.models.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import mohsen.soltanian.cleanarchitecture.core.data.scopes.moshi.NullToEmptyString

@JsonClass(generateAdapter = true)
data class Cast(
    @Json(name = "name")
    @NullToEmptyString
    val name: String,
    @Json(name= "character")
    @NullToEmptyString
    val character: String,
    @Json(name = "profile_path")
    @NullToEmptyString
    val profilePath: String
)
