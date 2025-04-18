// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.1")
        classpath("com.diffplug.spotless:spotless-plugin-gradle:6.24.0")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.1"
    id("com.diffplug.spotless") version "6.24.0" apply false
}

subprojects {
    afterEvaluate {
        project.apply(from = "$rootDir/analytic_code/detekt/detekt.gradle")
        project.apply(from = "$rootDir/analytic_code/spotless/spotless.gradle")
    }

}