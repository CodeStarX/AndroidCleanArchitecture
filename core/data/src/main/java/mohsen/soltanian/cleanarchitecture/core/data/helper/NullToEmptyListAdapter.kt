package mohsen.soltanian.cleanarchitecture.core.data.helper

import com.squareup.moshi.*
import java.io.IOException
import java.lang.reflect.Type

class NullToEmptyListAdapter(private val delegate: JsonAdapter<List<*>>) :
    JsonAdapter<List<*>?>() {
    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): List<*>? {
        if (reader.peek() == JsonReader.Token.NULL) {
            reader.skipValue()
            return emptyList<Any>()
        }
        return delegate.fromJson(reader)
    }

    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, value: List<*>?) {
        if(value == null) {
            delegate.toJson(writer, emptyList<Any>())
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
                if (Types.getRawType(type) != MutableList::class.java) {
                    return null
                }
                val objectJsonAdapter = moshi.nextAdapter<List<*>>(this, type, annotations)
                return NullToEmptyListAdapter(objectJsonAdapter)
            }
        }
    }
}