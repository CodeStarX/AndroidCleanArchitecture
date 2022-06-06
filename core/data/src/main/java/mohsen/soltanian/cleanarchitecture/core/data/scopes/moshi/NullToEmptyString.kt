package mohsen.soltanian.cleanarchitecture.core.data.scopes.moshi

import com.squareup.moshi.JsonQualifier

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class NullToEmptyString
