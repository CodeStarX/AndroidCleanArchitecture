import plugins.AndroidLibraryPlugin
import extensions.*

apply<AndroidLibraryPlugin>()
apply(plugin = GradlePlugins.Hilt)

dependencies {

    implementation(Deps.Retrofit.Base)
    implementation(Deps.Retrofit.Moshi)
    implementation(Deps.Moshi.Kotlin)
    implementation(Deps.Moshi.LazyAdapter)
    implementation(Deps.Okhttp.Base)
    implementation(Deps.Okhttp.LoggingInterceptor)

    implementation(Deps.Hilt.Android)
    kapt(Deps.Hilt.AndroidCompiler)

    api(Deps.Timber)
    api(Deps.Coroutine.Core)
    api(Deps.Coroutine.Android)
    api(Deps.Test.Json)
    api(Deps.Test.Okhttp)
    api(Deps.Test.Coroutine)

    debugImplementation(Deps.Chucker.Library)
    releaseImplementation(Deps.Chucker.NoLibrary)

    testImplementation(Deps.Test.Hamcrest)
    testImplementation(Deps.Test.kluent)
    testImplementation(Deps.Test.Mockk)
    testImplementation(Deps.Test.TestRules)
}