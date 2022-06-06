package plugins

import Configs
import FlavorDimensions
import GradlePlugins
import ProductFlavors
import com.android.build.api.dsl.BuildType
import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.*

class AppPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.applyPlugins()
        target.configureAndroid()
    }

    private fun Project.applyPlugins() {
        plugins.apply(GradlePlugins.AndroidApplication)
        plugins.apply(GradlePlugins.KotlinAndroid)
        plugins.apply(GradlePlugins.KotlinKapt)
        plugins.apply(GradlePlugins.KotlinParcelize)
        plugins.apply(GradlePlugins.Hilt)
    }

    @Suppress("SimpleDateFormat")
    private fun Project.configureAndroid() = this.extensions.getByType(AppExtension::class).run {
        compileSdkVersion(Configs.CompileSdk)
        defaultConfig.apply {
            applicationId = Configs.Id
            minSdk = Configs.MinSdk
            targetSdk = Configs.TargetSdk
            versionCode = Configs.VersionCode
            versionName = Configs.VersionName
            multiDexEnabled = true
            vectorDrawables.useSupportLibrary = true
            testInstrumentationRunner = Configs.AndroidJunitRunner
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
                        "META-INF/LGPL2.1",
                        "META-INF/MANIFEST.MF",
                        "META-INF/com.android.tools/proguard/coroutines.pro",
                        "META-INF/proguard/coroutines.pro"
                    )
                )
            }
        }
        dataBinding.apply { isEnabled = true }
        viewBinding.apply { isEnabled = true }

        setSigningConfigs(project)

        buildTypes.apply {
            getByName("release") {
                signingConfig = signingConfigs.getByName("signingConfigRelease")
                isDebuggable = false
                isMinifyEnabled = true
                isShrinkResources = true
                proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
                buildConfigStringField("BASE_URL", Configs.Release.BaseUrl)
                applicationVariants.all {
                    val variant = this
                    variant.outputs
                        .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
                        .forEach { output ->
                            val formattedDate = SimpleDateFormat("yyyy_MM_dd").format(Date())
                            val outputFileName = "AppName-${variant.baseName}-v${variant.versionName}(${formattedDate}).apk"
                            output.outputFileName = outputFileName
                        }
                }

            }
            getByName("debug") {
                isTestCoverageEnabled = true
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

    private fun BaseExtension.setSigningConfigs(project: Project) = signingConfigs {
        create("signingConfigRelease") {
            val keystorePropertiesFile = project.rootProject.file("keystore.properties")
            if (!keystorePropertiesFile.exists()) {
                System.err.println("📜 Missing release.keystore.properties file for release signing")
            } else {
                val keystoreProperties = Properties().apply {
                    load(FileInputStream(keystorePropertiesFile))
                }
                try {
                    storeFile =
                        project.rootProject.file(keystoreProperties["storeFile"] as String)
                    storePassword = keystoreProperties["storePassword"] as String
                    keyAlias = keystoreProperties["keyAlias"] as String
                    keyPassword = keystoreProperties["keyPassword"] as String
                } catch (e: Exception) {
                    System.err.println("📜 keystore.properties file is malformed")
                }
            }
        }
    }

    private fun BuildType.buildConfigStringField(name: String, value: String) {
        this.buildConfigField("String", name, "\"$value\"")
    }

}