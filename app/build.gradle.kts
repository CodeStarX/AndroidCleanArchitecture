import extensions.*
import plugins.AppPlugin

apply<AppPlugin>()
apply(plugin = GradlePlugins.Navigation)

dependencies {
    implementation(project(path = ":libraries:framework"))

    implementation(Deps.Hilt.Android)
    kapt(Deps.Hilt.AndroidCompiler)

    testImplementation(Deps.Test.Junit)
    androidTestImplementation(Deps.Test.JunitExt)
    androidTestImplementation(Deps.Test.EspressoCore)
}