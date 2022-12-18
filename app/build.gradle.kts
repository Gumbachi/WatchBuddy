import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

// Versions
val composeVersion = "1.4.0-alpha02"
val composeNavVersion = "2.6.0-alpha04"
val material2Version = "1.4.0-alpha02"
val material3Version = "1.1.0-alpha02"
val lifecycleVersion = "2.6.0-alpha03"
val accompanistVersion = "0.28.0"
val ktorVersion = "2.2.1"
val koinAndroidVersion = "3.3.0"
val apolloVersion = "3.7.2"
val realmVersion = "1.5.1"

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    kotlin("plugin.serialization") version "1.7.20"
    id("io.realm.kotlin") version "1.5.1"
    id("com.apollographql.apollo3").version("3.7.2")
}

android {
    namespace = "com.gumbachi.watchbuddy"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.gumbachi.watchbuddy"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "TMDB_KEY", gradleLocalProperties(rootDir).getProperty("TMDB_KEY"))
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
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
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources {
            resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}



dependencies {

    // Koin
    implementation("io.insert-koin:koin-androidx-compose:$koinAndroidVersion")

    // Android
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.activity:activity-compose:1.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")


    // Compose Dependencies
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")

    // Extra Compose Dependencies
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation("androidx.navigation:navigation-compose:$composeNavVersion")
    
    // Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-pager:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-pager-indicators:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-flowlayout:$accompanistVersion")


    // Material Dependencies
    implementation("androidx.compose.material:material-icons-extended:$material2Version")
    implementation("androidx.compose.material3:material3:$material3Version")

    // Coil
    implementation("io.coil-kt:coil-compose:2.2.2")

    // KTOR Client
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    // Kotlinx Libraries
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // MongoDB Realm
    implementation("io.realm.kotlin:library-sync:$realmVersion")

    // Apollo GraphQL
    implementation("com.apollographql.apollo3:apollo-runtime:$apolloVersion")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

apollo {
    packageName.set("com.gumbachi.watchbuddy")
}