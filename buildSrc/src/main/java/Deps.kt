object Deps {

    object AndroidX {
        const val CoreKtx = "androidx.core:core-ktx:${Versions.CoreKtx}"
        const val AppCompat = "androidx.appcompat:appcompat:${Versions.AppCompat}"
        const val Material = "com.google.android.material:material:${Versions.Material}"
        const val ConstraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.ConstraintLayout}"
        const val RecyclerView = "androidx.recyclerview:recyclerview:${Versions.RecyclerView}"

        const val ActivityKtx = "androidx.activity:activity-ktx:${Versions.ActivityKtx}"
        const val FragmentKtx = "androidx.fragment:fragment-ktx:${Versions.FragmentKtx}"
        const val Paging = "androidx.paging:paging-runtime-ktx:${Versions.Paging}"
    }

    object LegacySupport{
        const val LegacySupport = "androidx.legacy:legacy-support-v4:${Versions.LegacySupport}"
    }

    object FabCounter{
        const val fabCounter = "com.github.andremion:counterfab:${Versions.fabCounter}"
    }

    object Navigation {
        const val NavigationComponent = "androidx.navigation:navigation-fragment-ktx:${Versions.Navigation}"
        const val NavigationComponentUi = "androidx.navigation:navigation-ui-ktx:${Versions.Navigation}"

    }

    object Glide {
        const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
        const val compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    }

    object Lifecycle {
        const val ViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Lifecycle}"
        const val LiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.Lifecycle}"
        const val Runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.Lifecycle}"
    }

    object Coroutine {
        const val Core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Coroutine}"
        const val Android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Coroutine}"
    }

    object Hilt {
        const val Android = "com.google.dagger:hilt-android:${Versions.Hilt}"
        const val AndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.Hilt}"
    }

    object Moshi {
        const val Kotlin = "com.squareup.moshi:moshi-kotlin:${Versions.Moshi}"
        const val LazyAdapter = "com.serjltt.moshi:moshi-lazy-adapters:${Versions.MoshiLazy}"
    }

    object Retrofit {
        const val Base = "com.squareup.retrofit2:retrofit:${Versions.Retrofit}"
        const val Moshi = "com.squareup.retrofit2:converter-moshi:${Versions.Retrofit}"
    }

    object Chucker {
        const val Library = "com.github.chuckerteam.chucker:library:${Versions.Chucker}"
        const val NoLibrary = "com.github.chuckerteam.chucker:library-no-op:${Versions.Chucker}"
    }

    object Okhttp {
        const val Base = "com.squareup.okhttp3:okhttp:${Versions.Okhttp}"
        const val LoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.Okhttp}"
    }

    object Test {
        const val Junit = "junit:junit:${Versions.Junit}"
        const val JunitExt = "androidx.test.ext:junit:${Versions.JunitExt}"
        const val EspressoCore = "androidx.test.espresso:espresso-core:${Versions.EspressoCore}"
        const val Coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Coroutine}"
        const val Okhttp = "com.squareup.okhttp3:mockwebserver:${Versions.Okhttp}"
        const val LiveData = "androidx.arch.core:core-testing:${Versions.Arch}"
    }

    const val Timber = "com.jakewharton.timber:timber:${Versions.Timber}"
}