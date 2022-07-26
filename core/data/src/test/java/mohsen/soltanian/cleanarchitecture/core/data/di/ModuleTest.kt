package mohsen.soltanian.cleanarchitecture.core.data.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.squareup.moshi.Moshi
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import mohsen.soltanian.cleanarchitecture.core.data.config.RemoteConfig
import mohsen.soltanian.cleanarchitecture.core.data.core.DataUnitTest
import mohsen.soltanian.cleanarchitecture.core.data.enviroment.CoreEnvironment
import mohsen.soltanian.cleanarchitecture.core.data.network.interceptor.HttpRequestInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should contain`
import org.amshove.kluent.`should equal to`
import org.amshove.kluent.`should equal`
import org.junit.Test
import retrofit2.Retrofit


class ModuleTest : DataUnitTest() {

    @Test
    fun `verify provide Remote Config`() {
        val remoteConfig = mockk<RemoteConfig> {
            every { timeOut() } returns 30L
            every { isDev() } returns false
            every { baseUrl() } returns "https://api.themoviedb.org/3/"
            every { environment() } returns CoreEnvironment.PUBLIC
        }
        val module = mockk<Module>()
        every { module.provideRemoteConfig() } returns remoteConfig
        val config = module.provideRemoteConfig()
        config `should be instance of` RemoteConfig::class.java
        config.timeOut() `should equal` 30L
        config.isDev() `should equal to` false
        config.baseUrl() `should equal to` "https://api.themoviedb.org/3/"
        config.environment() `should equal` CoreEnvironment.PUBLIC

        verify(exactly = 1) { module.provideRemoteConfig() }
    }

    @Test
    fun `given HttpLoggingInterceptor when isDev is false`() {
        val loggingModelInstance = mockk<HttpLoggingInterceptor> {
            every { level } returns HttpLoggingInterceptor.Level.NONE
        }
        val module = mockk<Module>()
        every { module.provideHttpLoggingInterceptor(config = any()) } returns loggingModelInstance
        val httpLoggingInterceptorInstance = module.provideHttpLoggingInterceptor(config = RemoteConfig())
        httpLoggingInterceptorInstance `should be instance of` HttpLoggingInterceptor::class.java
        httpLoggingInterceptorInstance.level `should equal` HttpLoggingInterceptor.Level.NONE

        verify(exactly = 1) { module.provideHttpLoggingInterceptor(config = any())}
    }

    @Test
    fun `given HttpLoggingInterceptor when isDev is true`() {
        val remoteConfig = mockk<RemoteConfig> {
            every { isDev() } returns true
        }
        val moduleModel = spyk<Module>()
        val httpLoggingInterceptorInstance = moduleModel.provideHttpLoggingInterceptor(config = remoteConfig)
        httpLoggingInterceptorInstance `should be instance of` HttpLoggingInterceptor::class.java
        httpLoggingInterceptorInstance.level `should equal` HttpLoggingInterceptor.Level.BODY

        verify(exactly = 1) { moduleModel.provideHttpLoggingInterceptor(any())}
    }

    @Test
    fun `verify provide OkHttp`() {
        val remoteConfig = mockk<RemoteConfig> {
            every { isDev() } returns true
            every { timeOut() } returns 25L
        }
        val httpLoggingInterceptor = mockk<HttpLoggingInterceptor>()
        val chuckerInterceptor = mockk<ChuckerInterceptor>()
        val httpRequestInterceptor = mockk<HttpRequestInterceptor>()

        val module = spyk<Module>()
        val okHttpClient = module.provideOkHttpClient(config = remoteConfig,httpLoggingInterceptor= httpLoggingInterceptor,
            chuckerInterceptor= chuckerInterceptor,httpRequestInterceptor= httpRequestInterceptor)
        okHttpClient `should be instance of` OkHttpClient::class.java
        okHttpClient.connectTimeoutMillis `should equal` 25000
        okHttpClient.readTimeoutMillis `should equal` 25000
        okHttpClient.writeTimeoutMillis `should equal` 25000
        okHttpClient.interceptors.size `should equal` 3
        okHttpClient.interceptors `should contain` httpLoggingInterceptor
        okHttpClient.interceptors `should contain` chuckerInterceptor
        okHttpClient.interceptors `should contain` httpRequestInterceptor
        okHttpClient.followSslRedirects `should equal` true
        okHttpClient.followRedirects `should equal` true
        okHttpClient.retryOnConnectionFailure `should equal` true


        verify(exactly = 1){module.provideOkHttpClient(config = any(),httpLoggingInterceptor= any(),
            chuckerInterceptor= any(),httpRequestInterceptor= any())}

    }

    @Test
    fun `verify provide Moshi`() {
        val module = spyk<Module>()
        val moshi = module.provideMoshi()
        moshi `should be instance of` Moshi::class.java

        verify(exactly = 1) { module.provideMoshi() }
    }

    @Test
    fun `verify provide Retrofit `() {
        val remoteConfig = mockk<RemoteConfig> {
            every { baseUrl() } returns "https://api.themoviedb.org/3/"
        }
        val okHttpClient = mockk<OkHttpClient>()
        val moshi = mockk<Moshi>()
        val module = spyk<Module>()
        val retrofit = module.provideRetrofit(config = remoteConfig,okHttpClient= okHttpClient,moshi= moshi)
        retrofit `should be instance of` Retrofit::class.java

        verify(exactly = 1) { module.provideRetrofit(config = any(),okHttpClient= any(),moshi= any()) }
    }

}