package mohsen.soltanian.cleanarchitecture.core.data.core

import kotlin.reflect.KClass

@kotlin.annotation.Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class ServiceUnitTesting(val baseUrl: String, val clazz: KClass<*>)
