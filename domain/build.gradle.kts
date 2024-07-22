plugins {
    id("domain-convention")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
}

android {
    namespace = "pet.project.domain"
}
dependencies {
    implementation(libs.androidx.room.common)
    implementation(project(":core:utils"))
}
