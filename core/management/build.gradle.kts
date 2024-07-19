plugins {
    id("core-convention")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
}

android {
    namespace = "pet.project.management"
    compileSdk = 34
}
dependencies {
    implementation(project(":core:data"))
    implementation(project(":domain"))
}
