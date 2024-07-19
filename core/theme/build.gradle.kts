plugins {
    id("core-convention")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
}

android {
    namespace = "pet.project.theme"
    compileSdk = 34
}

dependencies {
    implementation(project(":domain"))
}