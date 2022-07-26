package mohsen.soltanian.cleanarchitecture.libraries.framework.core.base.annotation

@kotlin.annotation.Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class ActivityAttribute(val layoutId: Int = -1, val handleBackPress: Boolean = false,
                                   val handleDoubleBackPress: Boolean = false, val lockBackPress: Boolean = false, val checkNetworkConnection: Boolean = false)
