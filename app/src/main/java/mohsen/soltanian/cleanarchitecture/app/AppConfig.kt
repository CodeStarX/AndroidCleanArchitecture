package mohsen.soltanian.cleanarchitecture.app

import mohsen.soltanian.cleanarchitecture.BuildConfig
import mohsen.soltanian.cleanarchitecture.ui.activities.main.MainActivity
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.application.CoreConfig
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.application.CoreEnvironment


class AppConfig : CoreConfig() {
    override fun appName(): String {
        return "Application-Name"
    }

    override fun environment(): CoreEnvironment {
        return if (isDev()) {
            CoreEnvironment.DEV
        } else {
            CoreEnvironment.PROD
        }
    }

    override fun isDev(): Boolean {
        return BuildConfig.DEBUG
    }

    override fun uncaughtExceptionPage(): Class<*> {
        return MainActivity::class.java
    }

    override fun uncaughtExceptionMessage(): String {
        return "Unknown Error"
    }
}