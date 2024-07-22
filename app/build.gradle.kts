plugins {
    id("app-convention-plugin")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
}

android {
    namespace = "pet.project.todolist"
    defaultConfig {
        applicationId = "pet.project.todolist"
        versionCode = 1
        versionName = "1.0"
    }
}
dependencies {
    implementation(project(":core:network"))
    implementation(project(":core:data"))
    implementation(project(":core:utils"))
    implementation(project(":core:theme"))
    implementation(project(":core:management"))
    implementation(project(":core:database"))
    implementation(project(":feature:mainScreen"))
    implementation(project(":feature:task"))
    implementation(project(":feature:settings"))
    implementation(project(":feature:info"))
}

