plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.bondoman_pdd"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.bondoman_pdd"
        minSdk = 29
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    val core_version = "1.12.0"
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.activity:activity:1.8.2")
    implementation("androidx.annotation:annotation:1.7.1")
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation("androidx.fragment:fragment-ktx:1.5.6")
    implementation("androidx.core:core-ktx:$core_version")
    implementation("androidx.core:core-role:1.0.0")
    implementation("androidx.core:core-animation:1.0.0-rc01")
    implementation("androidx.core:core-performance:1.0.0")
    implementation("androidx.core:core-google-shortcuts:1.1.0")
    implementation("androidx.core:core-remoteviews:1.1.0-alpha01")
    implementation("androidx.core:core-splashscreen:1.1.0-alpha02")
    implementation("androidx.security:security-crypto:1.0.0")
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.7.2")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("com.github.EvanRupert:ExcelKt:v0.1.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.core:core-animation-testing:1.0.0-rc01")
}