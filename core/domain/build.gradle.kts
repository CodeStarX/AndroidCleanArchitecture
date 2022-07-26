import plugins.AndroidLibraryPlugin
import extensions.*

apply<AndroidLibraryPlugin>()
apply(plugin = GradlePlugins.Hilt)


dependencies {

    implementation(Deps.Hilt.Android)
    kapt(Deps.Hilt.AndroidCompiler)

    api(project(path= ":core:data"))
    api(Deps.AndroidX.Paging)

    testImplementation(Deps.Test.kluent)
    testImplementation(Deps.Test.Junit)
    testImplementation(Deps.Test.Mockk)
    testImplementation(Deps.Test.TestRules)

    androidTestImplementation(Deps.Test.JunitExt)
    androidTestImplementation(Deps.Test.EspressoCore)

}