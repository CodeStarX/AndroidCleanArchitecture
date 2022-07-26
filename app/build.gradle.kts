import extensions.*
import plugins.AppPlugin

apply<AppPlugin>()
apply(plugin = GradlePlugins.Navigation)

dependencies {
    implementation(project(path = ":libraries:framework"))

    implementation(Deps.Hilt.Android)
    kapt(Deps.Hilt.AndroidCompiler)



    testImplementation(Deps.Retrofit.Base)
    testImplementation(Deps.Retrofit.Moshi)
    testImplementation(Deps.Okhttp.Base)
    testImplementation(Deps.Okhttp.LoggingInterceptor)
    testImplementation(Deps.Test.LiveData)
    testImplementation(Deps.Test.Junit_Ext)
    testImplementation(Deps.Test.TestCoreExt)
    testImplementation(Deps.Test.Robolectric)
    testImplementation(Deps.Test.kluent)
    testImplementation(Deps.Test.Mockk)
    testImplementation(Deps.Test.Junit)
    androidTestImplementation(Deps.Test.JunitExt)
    androidTestImplementation(Deps.Test.EspressoCore)
}