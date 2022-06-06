package mohsen.soltanian.cleanarchitecture.app

import dagger.hilt.android.HiltAndroidApp
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.application.AppInitializer
import mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.application.CoreApplication
import javax.inject.Inject


@HiltAndroidApp
class Application: CoreApplication<AppConfig>() {

    @Inject
    lateinit var initializer: AppInitializer

    override fun appConfig(): AppConfig = AppConfig()

    override fun onCreate() {
        super.onCreate()
        initializer.init(this@Application)

    }
}