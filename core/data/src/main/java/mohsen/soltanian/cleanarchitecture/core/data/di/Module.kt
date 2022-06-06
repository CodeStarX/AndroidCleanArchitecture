package mohsen.soltanian.cleanarchitecture.core.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mohsen.soltanian.cleanarchitecture.core.data.config.RemoteConfig
import mohsen.soltanian.cleanarchitecture.core.data.helper.NullToEmptyStringAdapter
import mohsen.soltanian.cleanarchitecture.core.data.network.interceptor.HttpRequestInterceptor
import mohsen.soltanian.cleanarchitecture.core.data.scopes.ServerService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

private const val REMOTE_CONFIG = "remote_config"
private const val CONTENT_LENGTH = 250_000L


@InstallIn(SingletonComponent::class)
@Module
class Module {

    @Provides
    @Singleton
    @Named(value = REMOTE_CONFIG)
    fun provideRemoteConfig(): RemoteConfig {
        return RemoteConfig()
    }

    @Provides
    @Singleton
    fun provideHttpRequestInterceptor(): HttpRequestInterceptor {
        return HttpRequestInterceptor()
    }

    @Provides
    @Singleton
    fun provideChuckInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        // Create the Collector
        val chuckerCollector = ChuckerCollector(
            context = context,
            // Toggles visibility of the push notification
            showNotification = false,
            // Allows to customize the retention period of collected data
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        return ChuckerInterceptor.Builder(context)
            // The previously created Collector
            .collector(chuckerCollector)
            // The max body content length in bytes, after this responses will be truncated.
            .maxContentLength(CONTENT_LENGTH)
            // List of headers to replace with ** in the Chucker UI
            .redactHeaders("Auth-Token", "Bearer")
            // Read the whole response body even when the client does not consume the response completely.
            // This is useful in case of parsing errors or when the response body
            // is closed before being read like in Retrofit with Void and Unit types.
            .alwaysReadResponseBody(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(config: RemoteConfig): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (!config.isDev()) {
                HttpLoggingInterceptor.Level.NONE
            } else {
                HttpLoggingInterceptor.Level.BODY
            }
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named(value = REMOTE_CONFIG) config: RemoteConfig,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
        httpRequestInterceptor: HttpRequestInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
            if (config.isDev()) {
                addInterceptor(chuckerInterceptor)
                addInterceptor(httpRequestInterceptor)
            }
            connectTimeout(config.timeOut(), TimeUnit.SECONDS)
            readTimeout(config.timeOut(), TimeUnit.SECONDS)
            writeTimeout(config.timeOut(), TimeUnit.SECONDS)
            followSslRedirects(true)
            followRedirects(true)
            retryOnConnectionFailure(true)
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(NullToEmptyStringAdapter())
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    @ServerService
    fun provideRetrofit(
        @Named(value = REMOTE_CONFIG) config: RemoteConfig,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(config.baseUrl())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }



}