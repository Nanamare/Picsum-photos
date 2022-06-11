plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.nanamare.starter"
    compileSdk = Versions.compile_sdk_version

    defaultConfig {
        applicationId = "com.nanamare.starter"
        minSdk = Versions.min_sdk_version
        targetSdk = Versions.compile_sdk_version
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        dataBinding = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":core"))

    implementation(Deps.AndroidX.androidx_core_ktx)
    implementation(Deps.AndroidX.androidx_appcompat)
    implementation(Deps.AndroidX.androidx_paging)
    implementation(Deps.AndroidX.androidx_app_startup)
    implementation(Deps.AndroidX.androidx_lifecycle_runtime_ktx)
    implementation(Deps.AndroidX.androidx_lifecycle_view_model_ktx)
    implementation(Deps.AndroidX.androidx_activity_ktx)

    implementation(Deps.material_design)

    Deps.Retrofit.retrofit_dependencies.forEach(::implementation)

    Deps.Hilt.hilt_dependencies.forEach(::implementation)
    kapt(Deps.Hilt.dagger_hilt_compiler)

    Deps.Coil.coil_dependencies.forEach(::implementation)

    Deps.Test.unit_test_dependencies.forEach(::testImplementation)

    Deps.AndroidTest.android_unit_test_dependencies.forEach(::androidTestImplementation)

    implementation(Deps.subsampling_scale_image_view)

    implementation(Deps.timber)

    implementation(Deps.kotlin_coroutines)

    Deps.Room.room_dependencies.forEach(::implementation)
    kapt(Deps.Room.room_compiler)

}