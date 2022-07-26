package mohsen.soltanian.cleanarchitecture.core.data.helper

import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.mockk.InternalPlatformDsl.toArray
import io.mockk.mockk
import io.mockk.verify
import mohsen.soltanian.cleanarchitecture.core.data.core.DataUnitTest
import mohsen.soltanian.cleanarchitecture.core.data.extention.empty
import mohsen.soltanian.cleanarchitecture.core.data.extention.toJson
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should equal`
import org.junit.Test

class NullToEmptyListAdapterTest : DataUnitTest() {


    @JsonClass(generateAdapter = true)
    data class ListModel(
        @Json(name = "keys")
        val keys: List<String>?
    )

    data class ToJsonListModel(
        val keys: List<String>?= null
    )

    val moshi: Moshi = Moshi.Builder().add(NullToEmptyListAdapter.FACTORY).addLast(KotlinJsonAdapterFactory()).build()


    @Test
    fun `verify fromJson method when All values are null`() {
        val json = "{\"keys\": null}".trimIndent()
        val jsonAdapter = moshi.adapter(ListModel::class.java).serializeNulls().lenient()
        val model = jsonAdapter.fromJson(json)
        model `should be instance of` ListModel::class.java
        model?.keys `should be instance of` List::class.java
        model?.keys?.size `should equal` 0

    }

    @Test
    fun `verify toJson method when All values are null`() {
        val jsonAdapter = moshi.adapter(ToJsonListModel::class.java).serializeNulls().lenient()
        val strModel = jsonAdapter.toJson(ToJsonListModel())
        val model = jsonAdapter.fromJson(strModel)
        model `should be instance of` ToJsonListModel::class.java
        model?.keys `should be instance of` List::class.java
        model?.keys `should equal` listOf()

    }


}