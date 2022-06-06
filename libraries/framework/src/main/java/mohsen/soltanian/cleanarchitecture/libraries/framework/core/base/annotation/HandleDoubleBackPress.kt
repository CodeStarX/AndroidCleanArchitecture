package mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.annotation

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class HandleDoubleBackPress(val value: Boolean = false)