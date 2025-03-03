plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.re_kid.lis.correctratelog"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.re_kid.lis.correctratelog"
        minSdk = 26
        targetSdk = 34
        versionCode = 7
        versionName = "0.1.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}