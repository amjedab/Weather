plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)

  id("kotlin-kapt")
  id("kotlin-android")
  kotlin("kapt")
  id("com.google.dagger.hilt.android")
}

android {
  namespace = "com.sample.weather"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.sample.weather"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    debug {
      applicationIdSuffix = ".debug"
      isDebuggable = true
      isMinifyEnabled = false
      isShrinkResources = false
    }
    release {
      isMinifyEnabled = true
      isShrinkResources = true
      isDebuggable = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.1"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  implementation(libs.play.services.location)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.com.google.dagger.hilt.android.testing)
  implementation(libs.androidx.ui.test.junit4.android)

  //Debug
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)

  //Retrofit
  implementation(libs.squareup.retrofit2)
  implementation(libs.squareup.retrofit2.converter.gson)
  implementation(libs.squareup.retrofit2.adapter.rxjava)
  implementation(libs.squareup.okhttp3.logging.interceptor)

  //hilt
  implementation(libs.com.google.dagger.hilt.android)
  implementation(libs.androidx.hilt)
  kapt(libs.com.google.dagger.hilt.compiler)
  kapt(libs.com.google.dagger.hilt.android.compiler)

  //Jetpack Compose
  implementation(libs.io.coil.kt.coil.compose)

  //coroutines
  implementation(libs.org.jetbrains.kotlinx.coroutines.core)
  implementation(libs.org.jetbrains.kotlinx.coroutines.android)

  //Third Party Libraries
  implementation(libs.timber)
  implementation(libs.threetenabp)

}