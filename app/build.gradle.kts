plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android) // Add this line
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.google.services)
}

android {
    namespace = "ru.netology.nmedia"
    compileSdk = 36

    defaultConfig {
        applicationId = "ru.netology.nmedia"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    // REMOVE the kotlin block from here
}

// PLACE the kotlin block here (at the top level)
kotlin {
    jvmToolchain(11)
}

dependencies {
    // ... your dependencies remain the same
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.gson)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //implementation(platform(libs.firebase))
   // implementation(libs.firebase.messaging)

    implementation(libs.play.services)
    implementation(libs.google.firebase.messaging)
    coreLibraryDesugaring(libs.desugaring)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.room)
    implementation(libs.androidx.room.ktx) // Add this
    ksp(libs.androidx.room.compiler)

}

