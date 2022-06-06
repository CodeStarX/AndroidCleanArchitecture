package mohsen.soltanian.cleanarchitecture.core.data.helper

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import mohsen.soltanian.cleanarchitecture.core.data.scopes.moshi.NullToEmptyString

class NullToEmptyStringAdapter {
    @ToJson
    fun toJson(@NullToEmptyString value: String?): String? {
        return value
    }

    @FromJson
    @NullToEmptyString
    fun fromJson(@javax.annotation.Nullable data: String?): String? {
        return data ?: ""
    }
}