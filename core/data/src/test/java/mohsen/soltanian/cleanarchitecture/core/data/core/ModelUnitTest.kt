package mohsen.soltanian.cleanarchitecture.core.data.core

import android.annotation.SuppressLint
import io.mockk.MockKAnnotations
import mohsen.soltanian.cleanarchitecture.core.data.extention.toJson
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.json.JSONException
import org.json.JSONObject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import java.lang.reflect.Constructor

@RunWith(BlockJUnit4ClassRunner::class)
abstract class ModelUnitTest {
    private val parameters = ArrayList<String>()

    private val  modelTesting: ModelTesting? = javaClass.getAnnotation(ModelTesting::class.java)

    @SuppressLint("NewApi")
    private fun getConstructor(clazz: Class<*>?): Any? {
        val constructors = clazz?.declaredConstructors
        if (constructors?.isNotEmpty() == true) {
            val nonConstructor = findNonConstructor(constructors)
            nonConstructor.isAccessible = true
            return try {
                nonConstructor.newInstance(*arrayOfNulls(nonConstructor.parameters.size))
            } catch (ex: Exception) {
                ex.printStackTrace()
                null
            }
        }

        return null
    }

    private fun findNonConstructor(constructors: Array<Constructor<*>>): Constructor<*> {
        var originalConstructor = constructors.first()
        for (element in constructors) {
            if (!element.isSynthetic) {
                originalConstructor = element
            }
        }
        return originalConstructor
    }

    @SuppressLint("NewApi")
    @Throws(JSONException::class)
    @Before
    fun setUpTest() {
        MockKAnnotations.init(this)
                checkNotNull(modelTesting) {
            "you didn't use ModelTesting annotation."
        }
        val modelClass = modelTesting.clazz.javaObjectType
        val obj = getConstructor(modelClass)
        val responseJson = obj.toJson()
        val jsonObject = JSONObject(responseJson)
        val iterator = jsonObject.keys()
        parameters.clear()
        iterator.forEachRemaining { parameters.add(it) }
    }

    @Test
    fun `run test validate Model Fields`() {
        val list = modelTesting?.modelFields?.toList()
        val paramItems = Matchers.`is`(Matchers.`in`(parameters))
        val listItems = Matchers.`is`(Matchers.`in`(list))
        MatcherAssert.assertThat(list, CoreMatchers.everyItem(paramItems))
        MatcherAssert.assertThat(parameters, CoreMatchers.everyItem(listItems))
    }
}