plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.jvg.gpsapp.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    packaging {
        resources.excludes += "DebugProbesKt.bin"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    // Api
    implementation(projects.core.api)

    // Database
    implementation(projects.core.database)

    // Resources
    implementation(projects.core.resources)

    // Types
    implementation(projects.core.types)

    // Util
    implementation(projects.core.util)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    // Reflect
    implementation(libs.kotlin.reflect)

    // Konnection
    implementation(libs.konnection)

    // Koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)
}
