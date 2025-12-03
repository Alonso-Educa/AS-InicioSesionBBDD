plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.0.21-1.0.28"
    alias(libs.plugins.google.gms.google.services)
//    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.bbdd"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.bbdd"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:34.6.0"))
    implementation("com.google.firebase:firebase-auth")
    //ROOM
    api("androidx.room:room-runtime:2.8.3")
    implementation(libs.firebase.firestore)
    implementation("com.google.firebase:firebase-firestore:26.0.2")
    // implementation(libs.androidx.material3) // para la contraseña
     //  implementation(libs.androidx.compose.foundation) //text ofuscation
    ksp("androidx.room:room-compiler:2.8.3")
    implementation("androidx.room:room-ktx:2.8.3")
    implementation("androidx.navigation:navigation-compose:2.9.5") // Navegacion

    //---------------------------------
    implementation("androidx.compose.material:material-icons-extended:1.5.0-alpha08") // Para los iconosb
    implementation("androidx.compose.material3:material3:1.5.0-alpha08") // Para tener el material3 lo más actualizado de jetpack compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}


