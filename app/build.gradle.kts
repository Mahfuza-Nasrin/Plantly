

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.plantly"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.plantly"
        minSdk = 24
        targetSdk = 34
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Firebase BOM to manage versions automatically
    implementation(platform(libs.firebase.bom))

    // Firebase libraries (versions will be managed by BOM)
    implementation(libs.com.google.firebase.firebase.auth)
    implementation(libs.google.firebase.database)
    implementation(libs.google.firebase.storage)
    implementation(libs.google.firebase.firestore)

    // Google Play Services Auth for Google Sign-In
    implementation(libs.play.services.auth)

    //image downloading and caching library
    implementation(libs.picasso)
    implementation(libs.glide)

    //for cloudinary
    implementation(libs.cloudinary)




}