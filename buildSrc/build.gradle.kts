plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    google()
    jcenter()
    mavenCentral()
    gradlePluginPortal()
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(11))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

gradlePlugin {
    plugins {
        register("AppPlugin") {
            id = "AppPlugin"
            implementationClass = "plugins.AppPlugin"
        }
        register("AndroidLibraryPlugin") {
            id = "AndroidLibraryPlugin"
            implementationClass = "plugins.AndroidLibraryPlugin"
        }
        register("KotlinLibraryPlugin") {
            id = "KotlinLibraryPlugin"
            implementationClass = "plugins.KotlinLibraryPlugin"
        }
    }
}

object Versions {
    const val GRADLE = "7.2.0"
    const val KOTLIN = "1.6.10"
    const val HILT = "2.40.5"
    const val NAVIGATION = "2.4.2"
}

object Deps {
    const val ANDROID_GRADLE = "com.android.tools.build:gradle:${Versions.GRADLE}"
    const val KOTLIN_GRADLE = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
    const val HILT_GRADLE = "com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}"
    const val Navigation_GRADLE = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.NAVIGATION}"
}

dependencies {
    implementation(gradleApi())
    implementation(Deps.ANDROID_GRADLE)
    implementation(Deps.KOTLIN_GRADLE)
    implementation(Deps.HILT_GRADLE)
    implementation(Deps.Navigation_GRADLE)
}