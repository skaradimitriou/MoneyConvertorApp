plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.moneyconvertor.android.hilt)
    alias(libs.plugins.moneyconvertor.android.room)
}

android {
    namespace = "com.stathis.database"
}

dependencies {
    implementation(project(":core:model"))
}