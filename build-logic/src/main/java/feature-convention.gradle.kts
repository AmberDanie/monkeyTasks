import gradle.kotlin.dsl.accessors._50e0a7704a90f305f92a9b06d57a40e4.kapt
import gradle.kotlin.dsl.accessors._9812b1ced1002563071fd5803c2ee774.androidTestImplementation
import gradle.kotlin.dsl.accessors._9812b1ced1002563071fd5803c2ee774.debugImplementation
import gradle.kotlin.dsl.accessors._9812b1ced1002563071fd5803c2ee774.implementation
import gradle.kotlin.dsl.accessors._9812b1ced1002563071fd5803c2ee774.testImplementation
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {
    // divkit
    implementation(libs.yandex.div)
    implementation(libs.yandex.div.core)
    implementation(libs.yandex.div.json)
    implementation(libs.yandex.div.utils)
    implementation(libs.yandex.div.picasso)
    implementation(libs.yandex.div.zoom)
    implementation(libs.yandex.div.rive)
    implementation(libs.okhttp3)

    implementation(libs.dagger)
    implementation(libs.androidx.tools.core)
    kapt(libs.dagger.compiler)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

internal val Project.libs: LibrariesForLibs
    get() = (this as ExtensionAware).extensions.getByName("libs") as LibrariesForLibs
