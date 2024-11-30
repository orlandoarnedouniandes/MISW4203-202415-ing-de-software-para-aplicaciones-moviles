// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
}

buildscript {
    dependencies {
        // Add Safe Args Gradle plugin for Navigation
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.0")
    }
}
