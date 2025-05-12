plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.library)
    id("com.google.devtools.ksp")
}

android {
    namespace = "br.com.victorcs.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    kotlin {
        jvmToolchain(11)
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}

dependencies {
    //region Kotlin and Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlin.reflect)
    //endregion
    //region AndroidX KTX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.androidx.foundation)
    //endregion
    //region Theme
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
    //endregion
    //region Http
    implementation(libs.okhttp)
    implementation(libs.stetho.okhttp3)
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.stetho)
    debugImplementation(libs.mockwebserver)
    implementation(libs.logging.interceptor)
    //endregion
    //region Logging
    implementation(libs.timber)
    //endregion
    //region Moshi
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapters)
    ksp(libs.moshi.kotlin.codegen)
    implementation(libs.converter.moshi)
    //endregion
    //region Koin
    implementation(libs.koin.bom)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    //endregion
    //region Test
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.koin.test.junit4)
    //endregion
}