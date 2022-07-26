package mohsen.soltanian.cleanarchitecture.core.data.core


import mohsen.soltanian.cleanarchitecture.core.data.extention.moshi
import mohsen.soltanian.cleanarchitecture.core.data.services.RemoteApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.reflect.KClass

abstract class ServicesUnitTest : DataUnitTest() {

    private var baseUrl: String = ""
    private lateinit var serviceClass: KClass<*>
    lateinit var mockWebServerInstance: MockWebServer
    private val config: ServiceUnitTesting = javaClass.getAnnotation(ServiceUnitTesting::class.java) as ServiceUnitTesting

    private var okhttpInstance: OkHttpClient = OkHttpClient.Builder()
        .followSslRedirects(true)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    override fun onSetUpTest() {
        super.onSetUpTest()
        baseUrl = config.baseUrl
        serviceClass = config.clazz

        mockWebServerInstance = MockWebServer().apply {
            start()
        }
    }

    protected fun serviceBuilder(isLive: Boolean = false): RemoteApi {
        return if(isLive) {
            Retrofit.Builder()
                .client(okhttpInstance)
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(serviceClass.javaObjectType) as RemoteApi
        } else {
            Retrofit.Builder()
                .client(okhttpInstance)
                .baseUrl(mockWebServerInstance.url(""))
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(serviceClass.javaObjectType) as RemoteApi
        }
    }

    fun enqueueResponse(filePath: String) {
        val inputStream = javaClass.classLoader?.getResourceAsStream(filePath)
        val bufferSource = inputStream?.source()?.buffer() ?: return
        val mockResponse = MockResponse()

        mockWebServerInstance.enqueue(
            mockResponse.setBody(
                bufferSource.readString(Charsets.UTF_8)
            )
        )
    }

    override fun onStopTest() {
        super.onStopTest()
        mockWebServerInstance.shutdown()
    }
}