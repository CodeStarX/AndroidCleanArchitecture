package mohsen.soltanian.cleanarchitecture.core.data.helper

import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import mohsen.soltanian.cleanarchitecture.core.data.core.DataUnitTest
import mohsen.soltanian.cleanarchitecture.core.data.extention.empty
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should equal`
import org.junit.Test

class NullToEmptyStringAdapterTest : DataUnitTest() {

    @JsonClass(generateAdapter = true)
    data class ListModel(
        @Json(name = "keys")
        val keys: List<String>?
    )

    @JsonClass(generateAdapter = true)
    data class Model(
        @Json(name = "key")
        val key: String?
    )

    data class ToJsonModel(
        val key: String? = null
    )

    data class ToJsonListModel(
        val keys: List<String?> = listOf("value1",null,"value3")
    )


    val moshi: Moshi = Moshi.Builder().add(NullToEmptyStringAdapter.FACTORY).addLast(KotlinJsonAdapterFactory()).build()


    @Test
    fun `verify fromJson method when One of the values is null`() {
        val json = "{\"keys\": [null,\"value2\",\"value3\"]}".trimIndent()
        val jsonAdapter = moshi.adapter(ListModel::class.java).serializeNulls().lenient()
        val model = jsonAdapter.fromJson(json)
        model `should be instance of` ListModel::class.java
        model?.keys `should be instance of` List::class.java
        model?.keys?.size `should equal` 3
        model?.keys?.get(0) `should equal` String.empty()
        model?.keys?.get(1) `should equal` "value2"
        model?.keys?.get(2) `should equal` "value3"

    }

    @Test
    fun `verify fromJson method when All values are valid`() {
        val json = "{\"keys\": [\"value1\",\"value2\",\"value3\"]}".trimIndent()
        val jsonAdapter = moshi.adapter(ListModel::class.java).serializeNulls().lenient()
        val model = jsonAdapter.fromJson(json)
        model `should be instance of` ListModel::class.java
        model?.keys `should be instance of` List::class.java
        model?.keys?.size `should equal` 3
        model?.keys?.get(0) `should equal` "value1"
        model?.keys?.get(1) `should equal` "value2"
        model?.keys?.get(2) `should equal` "value3"

    }


    @Test
    fun `verify fromJson method when value is null`() {
        val json = "{\"key\": null}".trimIndent()
        val jsonAdapter = moshi.adapter(Model::class.java).serializeNulls().lenient()
        val model = jsonAdapter.fromJson(json)
        model `should be instance of` Model::class.java
        model?.key `should be instance of` String::class.java
        model?.key `should equal` String.empty()

    }

    @Test
    fun `verify fromJson method when value is valid`() {
        val json = "{\"key\": \"value1\"}".trimIndent()
        val jsonAdapter = moshi.adapter(Model::class.java).serializeNulls().lenient()
        val model = jsonAdapter.fromJson(json)
        model `should be instance of` Model::class.java
        model?.key `should be instance of` String::class.java
        model?.key `should equal` "value1"
    }

    @Test
    fun `verify toJson method when value is null`() {
      val jsonAdapter = moshi.adapter(ToJsonModel::class.java).serializeNulls().lenient()
       val strModel = jsonAdapter.toJson(ToJsonModel())

       val model = jsonAdapter.fromJson(strModel)
        model `should be instance of` ToJsonModel::class.java
        model?.key `should be instance of` String::class.java
        model?.key `should equal` String.empty()
    }

    @Test
    fun `verify toJson method when One of the values is null`() {
        val jsonAdapter = moshi.adapter(ToJsonListModel::class.java).serializeNulls().lenient()
        val strModel = jsonAdapter.toJson(ToJsonListModel())

        val model = jsonAdapter.fromJson(strModel)
        model `should be instance of` ToJsonListModel::class.java
        model?.keys `should be instance of` List::class.java
        model?.keys?.size `should equal` 3
        model?.keys?.get(0) `should equal` "value1"
        model?.keys?.get(1) `should equal` String.empty()
        model?.keys?.get(2) `should equal` "value3"
    }

}