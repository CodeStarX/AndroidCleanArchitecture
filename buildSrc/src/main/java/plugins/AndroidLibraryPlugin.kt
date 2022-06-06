package plugins

import Configs
import GradlePlugins
import com.android.build.api.dsl.BuildType
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.applyPlugins()
        target.configureAndroid()
    }

    private fun Project.applyPlugins() {
        plugins.apply(GradlePlugins.AndroidLibrary)
        plugins.apply(GradlePlugins.KotlinAndroid)
        plugins.apply(GradlePlugins.KotlinKapt)
        plugins.apply(GradlePlugins.KotlinParcelize)
    }

    private fun Project.configureAndroid() = this.extensions.getByType(LibraryExtension::class).run {
        compileSdk = Configs.CompileSdk
        defaultConfig.apply {
            minSdk = Configs.MinSdk
            targetSdk = Configs.TargetSdk
            versionCode = Configs.VersionCode
            versionName = Configs.VersionName
            multiDexEnabled = true
            vectorDrawables.useSupportLibrary = true
            testInstrumentationRunner = Configs.AndroidJunitRunner
            //consumerProguardFiles("consumer-rules.pro")
        }

        compileOptions.apply {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        packagingOptions.apply {
            resources {
                setExcludes(
                    setOf(
                        "META-INF/metadata.kotlin_module",
                        "META-INF/metadata.jvm.kotlin_module",
                        "META-INF/AL2.0",
                        "META-INF/LGPL2.1"
                    )
                )
            }
        }

        dataBinding.apply { isEnabled = true }
        viewBinding.apply { isEnabled = true }

        buildTypes.apply {
            getByName("release") {
                isMinifyEnabled = true
                proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
                buildConfigStringField("BASE_URL", Configs.Release.BaseUrl)
            }
            getByName("debug") {
                proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
                buildConfigStringField("BASE_URL", Configs.Debug.BaseUrl)
            }
        }

        flavorDimensions(FlavorDimensions.DEFAULT)
        productFlavors {
            create(ProductFlavors.DEV) {
                dimension = FlavorDimensions.DEFAULT
            }
            create(ProductFlavors.INTERNAL) {
                dimension = FlavorDimensions.DEFAULT
            }
            create(ProductFlavors.PUBLIC) {
                dimension = FlavorDimensions.DEFAULT
            }
        }

        variantFilter {
            ignore = listOf("devRelease","internalDebug","publicDebug").contains(element = name)
        }

    }

    private fun BuildType.buildConfigStringField(name: String, value: String) {
        this.buildConfigField("String", name, "\"$value\"")
    }
}