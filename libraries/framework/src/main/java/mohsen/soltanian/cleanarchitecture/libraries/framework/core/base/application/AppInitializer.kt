package mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.application

interface AppInitializer {
    fun init(application: CoreApplication<*>)
}