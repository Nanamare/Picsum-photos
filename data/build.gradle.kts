plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.nanamare.data"
    compileSdk = Versions.compile_sdk_version

    defaultConfig {
        minSdk = Versions.min_sdk_version
        targetSdk = Versions.compile_sdk_version

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", "\"https://picsum.photos\"")
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
}

dependencies {
    implementation(project(":domain"))

    Deps.Retrofit.retrofit_dependencies.forEach(::implementation)

    implementation(Deps.Hilt.dagger)
    implementation(Deps.kotlin_coroutines)

    Deps.Room.room_dependencies.forEach(::implementation)
    kapt(Deps.Room.room_compiler)
}