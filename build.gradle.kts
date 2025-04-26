// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.detekt.formatting)
        classpath(libs.spotless.plugin.gradle)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.1"
    id("com.diffplug.spotless") version "6.24.0" apply false
    alias(libs.plugins.android.library) apply false
}

subprojects {
    afterEvaluate {
        project.apply(from = "$rootDir/analytic_code/detekt/detekt.gradle")
        project.apply(from = "$rootDir/analytic_code/spotless/spotless.gradle")
    }
    plugins.withId("com.android.library") {
        extensions.configure<com.android.build.gradle.LibraryExtension>("android") {
            compileSdk = 35

            defaultConfig {
                minSdk = 24
                targetSdk = 35
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }

            buildFeatures {
                compose = true
                buildConfig = true
            }

            composeOptions {
                kotlinCompilerExtensionVersion = "1.5.0"
            }

            packaging {
                resources.excludes.addAll(
                    listOf("**/MANIFEST.MF", "META-INF/LICENSE.md", "META-INF/LICENSE-notice.md")
                )
            }

            testOptions {
                animationsDisabled = true
            }
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "11"
    }
}