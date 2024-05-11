plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.hyep.movietracker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hyep.movietracker"
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

    buildFeatures {
        viewBinding = true
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")
    //RxJava3 with Retrofit
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.11.0")

    //GSON
    implementation ("com.google.code.gson:gson:2.10.1")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    //Paging
    implementation("androidx.paging:paging-runtime:3.2.1")
    // optional - RxJava3 support
    implementation("androidx.paging:paging-rxjava3:3.2.1")


    //Hilt Dagger
    implementation("com.google.dagger:hilt-android:2.44")

    //ViewModel
    val lifecycle_version = "2.7.0"
    val arch_version = "2.2.0"
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")


}