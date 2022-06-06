package mohsen.soltanian.cleanarchitecture.core.data.config

import mohsen.soltanian.cleanarchitecture.core.data.BuildConfig
import mohsen.soltanian.cleanarchitecture.core.data.enviroment.CoreEnvironment
import javax.inject.Inject


class RemoteConfig @Inject constructor()   {

     private val timeOut: Long = 25

    fun environment(): CoreEnvironment {
        return if (isDev()) {
            CoreEnvironment.DEV
        } else {
            CoreEnvironment.PUBLIC
        }
    }

     fun baseUrl(): String {
        return when (environment()) {
            CoreEnvironment.DEV,  -> {
                BuildConfig.BASE_URL
            }
            CoreEnvironment.INTERNAL, CoreEnvironment.PUBLIC -> {
                BuildConfig.BASE_URL
            }
        }
    }

    fun timeOut(): Long {
        return timeOut
    }

    fun isDev(): Boolean {
        return BuildConfig.DEBUG
    }
}