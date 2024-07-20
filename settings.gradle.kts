pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        kotlin("jvm") version "2.0.0"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "toDoList"
include(":app")
include(":core")
include(":domain")
include(":core:theme")
include(":core:data")
include(":core:network")
include(":core:utils")
include(":core:database")
include(":core:management")
include(":feature")
include(":feature:mainScreen")
include(":feature:task")
include(":feature:settings")
include(":feature:info")
