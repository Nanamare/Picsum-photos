object Deps {

    const val gradle = "com.android.tools.build:gradle:${Versions.gradle_version}"
    const val hilt_gradle_plugin =
        "com.google.dagger:hilt-android-gradle-plugin:${Versions.Hilt.dagger_hilt_version}"

    object Retrofit {
        private const val retrofit =
            "com.squareup.retrofit2:retrofit:${Versions.Retrofit.retrofit_version}"
        private const val kotlinx_serialization_json =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KotlinxSerialization.json_version}"
        private const val kotlinx_serialization_retrofit_converter =
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.KotlinxSerialization.converter_version}"
        private const val kotlinx_serialization_core =
            "org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.KotlinxSerialization.core_version}"
        private const val okhttp =
            "com.squareup.okhttp3:okhttp:${Versions.Retrofit.ok_http_version}"
        private const val okhttp_logging_interceptor =
            "com.squareup.okhttp3:logging-interceptor:${Versions.Retrofit.ok_http_version}"

        val retrofit_dependencies = listOf(
            retrofit,
            kotlinx_serialization_retrofit_converter,
            kotlinx_serialization_json,
            kotlinx_serialization_core,
            okhttp,
            okhttp_logging_interceptor
        )
    }

    object Hilt {
        private const val dagger_hilt_android =
            "com.google.dagger:hilt-android:${Versions.Hilt.dagger_hilt_version}"
        private const val dagger_hilt_lifecycle_viewmodel =
            "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.Hilt.hilt_lifecycle_viewmodel_version}"
        const val dagger_hilt_compiler =
            "com.google.dagger:hilt-compiler:${Versions.Hilt.dagger_hilt_version}"
        const val dagger = "com.google.dagger:dagger:${Versions.Hilt.dagger_hilt_version}"

        val hilt_dependencies =
            listOf(dagger_hilt_android, dagger_hilt_lifecycle_viewmodel)
    }

    const val timber = "com.jakewharton.timber:timber:${Versions.timber_version}"

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.gradle_version}"
    const val kotlin_coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinx_coroutines_version}"

    object Coil {
        private const val coil = "io.coil-kt:coil:${Versions.coil_version}"
        private const val coil_base = "io.coil-kt:coil-base:${Versions.coil_version}"
        val coil_dependencies = listOf(coil, coil_base)
    }

    object AndroidX {
        const val androidx_activity_ktx =
            "androidx.activity:activity-ktx:${Versions.androidx_ktx_version}"
        const val androidx_core_ktx = "androidx.core:core-ktx:${Versions.Androidx.core_ktx_version}"
        const val androidx_appcompat =
            "androidx.appcompat:appcompat:${Versions.androidx_ktx_version}"
        const val androidx_lifecycle_runtime_ktx =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.Androidx.lifecycle_version}"
        const val androidx_app_startup =
            "androidx.startup:startup-runtime:${Versions.Androidx.startup_version}"
        const val androidx_paging =
            "androidx.paging:paging-runtime:${Versions.Androidx.paging_version}"
        const val androidx_lifecycle_view_model_ktx =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Androidx.lifecycle_version}"
    }

    const val material_design = "com.google.android.material:material:${Versions.material_version}"

    // RemoteMediator 로 활용 예정
    object Room {
        private const val room = "androidx.room:room-ktx:${Versions.room_version}"
        private const val room_runtime = "androidx.room:room-runtime:${Versions.room_version}"
        private const val room_paging = "androidx.room:room-paging:${Versions.room_version}"
        const val room_compiler = "androidx.room:room-compiler:${Versions.room_version}"

        val room_dependencies = listOf(room, room_runtime, room_paging)
    }

    object Test {
        private const val coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0"
        private const val arch_test = "androidx.arch.core:core-testing:2.0.0"
        private const val hamcrest = "org.hamcrest:hamcrest-library:1.3"
        private const val junit = "junit:junit:4.13.2"
        private const val paging_test = "androidx.paging:paging-common:3.1.1"
        private const val mockito_core = "org.mockito:mockito-core:4.0.0"
        private const val mockito_inline = "org.mockito:mockito-inline:3.2.4"

        val unit_test_dependencies = listOf(
            coroutines_test,
            arch_test,
            hamcrest,
            junit,
            paging_test,
            mockito_core,
            mockito_inline
        )
    }

    object AndroidTest {
        private const val junit = "androidx.test.ext:junit:1.1.3"
        private const val espresso_test = "androidx.test.espresso:espresso-core:3.4.0"
        private const val mockito_android = "org.mockito:mockito-android:3.8.0"

        val android_unit_test_dependencies = listOf(junit, espresso_test, mockito_android)
    }

    const val subsampling_scale_image_view =
        "com.davemorrissey.labs:subsampling-scale-image-view:${Versions.subsampling_scale_iv_versions}"

}