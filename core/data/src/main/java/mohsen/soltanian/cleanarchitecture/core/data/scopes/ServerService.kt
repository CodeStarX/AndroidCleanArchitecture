package mohsen.soltanian.cleanarchitecture.core.data.scopes

import javax.inject.Qualifier

/** Annotation for Retrofit dependency.  */
@Qualifier
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FIELD
)
annotation class ServerService()
