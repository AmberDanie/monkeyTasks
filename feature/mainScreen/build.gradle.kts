plugins {
    id("feature-convention")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
}

android {
    namespace = "pet.project.mainScreen"
}
dependencies {
    implementation(project(":domain"))
    implementation(project(":core:theme"))
    implementation(project(":core:data"))
}
