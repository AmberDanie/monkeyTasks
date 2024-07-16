import com.android.build.gradle.LibraryExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.JavaVersion
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.version

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
}

configure<LibraryExtension> {
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
            buildConfigField("String", "BASE_URL", "\"https://hive.mrdekk.ru/todo/\"")
            buildConfigField("String", "BEARER_TOKEN", System.getProperty("bearerToken"))
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"https://hive.mrdekk.ru/todo/\"")
            buildConfigField("String", "BEARER_TOKEN", System.getProperty("bearerToken"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("com.google.dagger:dagger")
    implementation("androidx.privacysandbox.tools:tools-core:1.0.0-alpha09")
    kapt("com.google.dagger:dagger-compiler")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx")
    implementation("androidx.room:room-runtime")
    ksp("androidx.room:room-compiler")
    implementation("androidx.room:room-ktx")
    implementation("androidx.datastore:datastore-preferences")
    implementation("androidx.datastore:datastore-preferences-core")
    implementation("androidx.datastore:datastore")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter")
    implementation("com.squareup.retrofit2:converter-gson")
    implementation("com.squareup.retrofit2:retrofit")
    implementation("androidx.core:core-ktx")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx")
    implementation("androidx.activity:activity-compose")
    implementation(platform("androidx.compose:compose-bom"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics" )
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3-android")
    implementation("androidx.navigation:navigation-compose")
    testImplementation("junit:junit")
    androidTestImplementation("androidx.test.ext:junit")
    androidTestImplementation("androidx.test.espresso:espresso-core")
    androidTestImplementation(platform("androidx.compose:compose-bom"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
