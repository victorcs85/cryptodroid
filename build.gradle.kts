@file:Suppress("DEPRECATION")

import io.gitlab.arturbosch.detekt.Detekt

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
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.spotless) apply false
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
                //noinspection OldTargetApi
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

            @Suppress("UnstableApiUsage")
            testOptions {
                animationsDisabled = true
                unitTests.all {
                    it.reports.html.required.set(true)
                }
            }
        }
    }
    tasks.withType(Detekt::class.java).configureEach {
        val typeResolutionEnabled = !classpath.isEmpty
        if (typeResolutionEnabled && project.hasProperty("precommit")) {
            // We must exclude kts files from pre-commit hook to prevent detekt from crashing
            // This is a workaround for the https://github.com/detekt/detekt/issues/5501
            exclude("*.gradle.kts")
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "11"
    }
}

tasks.withType<Detekt>().configureEach {
    if (project.hasProperty("precommit")) {
        val rootDir = project.rootDir
        val projectDir = projectDir

        val fileCollection = files()

        setSource(
            getGitStagedFiles(rootDir)
                .map { stagedFiles ->
                    val stagedFilesFromThisProject = stagedFiles
                        .filter { it.startsWith(projectDir) }

                    fileCollection.setFrom(*stagedFilesFromThisProject.toTypedArray())

                    fileCollection.asFileTree
                }
        )
    }
}
// Detekt task to run on pre-commit hook, like this url solution https://detekt.dev/docs/gettingstarted/git-pre-commit-hook/
fun Project.getGitStagedFiles(rootDir: File): Provider<List<File>> {
    return providers.provider {
        val outputStream = java.io.ByteArrayOutputStream()
        exec {
            commandLine("git", "--no-pager", "diff", "--name-only", "--cached")
            standardOutput = outputStream
        }
        outputStream.toString()
            .trim()
            .split("\n")
            .filter { it.isNotBlank() }
            .map { File(rootDir, it) }
    }
}
