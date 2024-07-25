
plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.mpesaintegrationtoandroid"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mpesaintegrationtoandroid"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        buildConfig = true // Enable BuildConfig feature
    }
    // set mpesa keys
    buildTypes.forEach {
        it.buildConfigField ("String", "CONSUMER_KEY", "\"${project.findProperty("DARAJA_CONSUMER_KEY")}\"")
        it.buildConfigField ("String", "CONSUMER_SECRET", "\"${project.findProperty("DARAJA_CONSUMER_SECRET")}\"")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.gson)
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Add Retrofit dependency
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Add Gson converter if needed
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3") // Logging interceptor
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}