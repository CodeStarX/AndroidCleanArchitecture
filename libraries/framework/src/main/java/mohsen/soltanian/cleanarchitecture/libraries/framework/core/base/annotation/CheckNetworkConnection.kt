package mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.annotation

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class CheckNetworkConnection(val value: Boolean = false)