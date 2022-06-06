package mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.application

interface CoreConfigProvider<T : CoreConfig> {
    fun appConfig(): T
}
