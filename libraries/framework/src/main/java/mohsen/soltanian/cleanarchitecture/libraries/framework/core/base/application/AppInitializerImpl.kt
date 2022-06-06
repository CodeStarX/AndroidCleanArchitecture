package mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.application

class AppInitializerImpl(private vararg val initializers: AppInitializer) : AppInitializer {
    override fun init(application: CoreApplication<*>) {
        initializers.forEach {
            it.init(application)
        }
    }
}