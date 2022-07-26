package mohsen.soltanian.cleanarchitecture.core.data.helper

import com.squareup.moshi.*
import mohsen.soltanian.cleanarchitecture.core.data.extention.empty
import java.io.IOException
import java.lang.reflect.Type

class NullToEmptyStringAdapter(
    val delegate: JsonAdapter<String>) :
    JsonAdapter<String?>() {
    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): String? {
        if (reader.peek() == JsonReader.Token.NULL) {
            reader.skipValue()
            return String.empty()
        }
        return delegate.fromJson(reader)
    }

    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, value: String?) {
        if(value == null) {
            delegate.toJson(writer, String.empty())
        }else {
            delegate.toJson(writer, value)
        }
    }

    companion object {
        val FACTORY: Factory = object : Factory {
            override fun create(
                type: Type,
                annotations: Set<Annotation?>,
                moshi: Moshi
            ): JsonAdapter<*>? {
                if (annotations.isNotEmpty()) {
                    return null
                }
                if (Types.getRawType(type) != String::class.java) {
                    return null
                }
                val objectJsonAdapter = moshi.nextAdapter<String>(this, type, annotations)
                return NullToEmptyStringAdapter(objectJsonAdapter)
            }
        }
    }
}