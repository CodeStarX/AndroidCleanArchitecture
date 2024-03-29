package mohsen.soltanian.cleanarchitecture.core.data.extention

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import mohsen.soltanian.cleanarchitecture.core.data.helper.NullToEmptyListAdapter
import mohsen.soltanian.cleanarchitecture.core.data.helper.NullToEmptyStringAdapter

val moshi: Moshi = Moshi.Builder()
    .add(NullToEmptyStringAdapter.FACTORY)
    .add(NullToEmptyListAdapter.FACTORY)
    .addLast(KotlinJsonAdapterFactory())
    .build()

inline fun <reified T> String.fromJson(): T? {
    return try {
        val jsonAdapter = moshi.adapter(T::class.java)
        jsonAdapter.fromJson(this)
    } catch (ex: Exception) {
        null
    }
}

inline fun <reified T> String.fromJsonList(): List<T>? {
    return try {
        val type = Types.newParameterizedType(MutableList::class.java, T::class.java)
        val jsonAdapter: JsonAdapter<List<T>> = moshi.adapter(type)
        jsonAdapter.fromJson(this)
    } catch (ex: Exception) {
        null
    }
}

inline fun <reified T> T.toJson(): String {
    return try {
        val jsonAdapter = moshi.adapter(T::class.java).serializeNulls().lenient()
        jsonAdapter.toJson(this)
    } catch (ex: Exception) {
        ""
    }
}
