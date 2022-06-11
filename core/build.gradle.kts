plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.nanamare.base"
    compileSdk = Versions.compile_sdk_version

    defaultConfig {
        minSdk = Versions.min_sdk_version
        targetSdk = Versions.compile_sdk_version

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kapt {
        correctErrorTypes = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }

}

dependencies {
    implementation(Deps.kotlin_coroutines)
    implementation(Deps.AndroidX.androidx_appcompat)
    implementation(Deps.AndroidX.androidx_core_ktx)
    implementation(Deps.AndroidX.androidx_activity_ktx)
}