import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.jvg.gpsapp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.jvg.gpsapp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = libs.versions.version.code.get().toInt()
        versionName = libs.versions.version.name.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    packaging {
        resources.excludes += "DebugProbesKt.bin"
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("boolean", "IS_DEBUG", "false")
            val baseUrl: String =
                gradleLocalProperties(rootProject.rootDir, providers).getProperty("PROD_URL")
            buildConfigField("String", "BASE_URL", baseUrl)
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
            buildConfigField("Boolean", "IS_DEBUG", "true")
            val baseUrl: String =
                gradleLocalProperties(rootProject.rootDir, providers).getProperty("DEV_URL")
            buildConfigField("String", "BASE_URL", baseUrl)
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_21.toString()
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    // Api
    implementation(projects.core.api)

    // Database
    implementation(projects.core.database)

    // Di
    implementation(projects.core.di)

    // Resources
    implementation(projects.core.resources)

    // UI
    implementation(projects.core.ui)

    // Util
    implementation(projects.core.util)

    // Shared
    implementation(projects.shared)

    /* Android / Compose */
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)

    // UI
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.compose.material3)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Tooling
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)

    /* Coroutines */
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    /* Dates */
    implementation(libs.kotlinx.datetime)

    /* Reflection */
    implementation(libs.kotlin.reflect)

    /* Koin */
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.koin.compose.viewmodel.navigation)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.compose.navigation)

    /* Network */

    // Connection
    implementation(libs.konnection)
}
