import plugins.AndroidLibraryPlugin
import extensions.*

apply<AndroidLibraryPlugin>()
apply(plugin = GradlePlugins.Hilt)

dependencies {

    implementation(Deps.Hilt.Android)
    kapt(Deps.Hilt.AndroidCompiler)

    api(project(":core:domain"))
    api(Deps.AndroidX.AppCompat)
    api(Deps.LegacySupport.LegacySupport)
    api(Deps.AndroidX.CoreKtx)
    api(Deps.AndroidX.Material)
    api(Deps.AndroidX.ConstraintLayout)
    api(Deps.AndroidX.RecyclerView)
    api(Deps.Lifecycle.ViewModel)
    api(Deps.Lifecycle.LiveData)
    api(Deps.Lifecycle.Runtime)
    api(Deps.AndroidX.ActivityKtx)
    api(Deps.AndroidX.FragmentKtx)
    api(Deps.Navigation.NavigationComponent)
    api(Deps.Navigation.NavigationComponentUi)
    api(Deps.Glide.glide)
    api(Deps.FabCounter.fabCounter)

    kapt(Deps.Glide.compiler)

    testImplementation(Deps.Test.Junit)
    androidTestImplementation(Deps.Test.JunitExt)
    androidTestImplementation(Deps.Test.EspressoCore)
}