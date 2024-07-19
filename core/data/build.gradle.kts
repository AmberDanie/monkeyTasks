plugins {
    id("core-convention")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
}
android {
    namespace = "pet.project.data"
}
dependencies {
    implementation(project(":core:utils"))
    implementation(project(":domain"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))
    implementation(project(":core:theme"))
}
