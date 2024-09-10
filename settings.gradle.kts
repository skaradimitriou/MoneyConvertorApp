gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))

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
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MoneyConvertor"
include(":app")
include(":core:network")
include(":core:common")
include(":core:model")
include(":core:data")
include(":core:database")
include(":core:ui")
include(":core:designsystem")
include(":feature")
include(":feature:currency-converter")
