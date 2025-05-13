plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.gestionstockapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.gestionstockapp"
        minSdk = 24
        targetSdk = 35
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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Libs gérées par versions catalog (libs.XXX)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Room (base de données locale)
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    // RecyclerView (affichage liste)
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Material Design (UI stylée)
    implementation("com.google.android.material:material:1.11.0")

    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

}
