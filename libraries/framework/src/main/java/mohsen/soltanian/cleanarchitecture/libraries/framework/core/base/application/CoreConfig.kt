package mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.application

abstract class CoreConfig {
    abstract fun appName(): String

    abstract fun environment(): CoreEnvironment

    open fun isDev() = false

    open fun uncaughtExceptionPage(): Class<*>? = null

    open fun uncaughtExceptionMessage(): String? = null
}
