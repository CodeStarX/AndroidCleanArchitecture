package mohsen.soltanian.cleanarchitecture.core.data.models.response

import com.squareup.moshi.Json

data class CastsResponse(
    @Json(name = "id")
    val id: Long?,
    @Json(name = "cast")
    val cast: List<Cast>?
) {
    companion object {
        val default = CastsResponse(id = 0, cast = listOf())
    }
}