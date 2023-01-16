import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties



plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    kotlin("plugin.serialization") version "1.7.20"
    id("io.realm.kotlin") version "1.5.1"
    id("com.apollographql.apollo3") version "3.7.3"
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

    // Compose Destinations Related
    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/${name}/kotlin")
            }
        }
    }
}


// Versions
val composeVersion = "1.4.0-alpha03"
val composeNavVersion = "2.6.0-alpha04"
val material2Version = "1.4.0-alpha03"
val material3Version = "1.1.0-alpha03"
val lifecycleVersion = "2.5.1"
val accompanistVersion = "0.28.0"
val ktorVersion = "2.2.1"
val koinAndroidVersion = "3.4.0"
val apolloVersion = "3.7.3"
val realmVersion = "1.5.1"


dependencies {


    // Compose Dependencies
    val composeBOM = platform("androidx.compose:compose-bom:2022.12.00")
    implementation(composeBOM)
    testImplementation(composeBOM)
    implementation("androidx.compose.material3:material3")

    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Icons
    implementation("androidx.compose.material:material-icons-extended")

    // Koin
    implementation("io.insert-koin:koin-androidx-compose:$koinAndroidVersion")

    // Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-pager:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-pager-indicators:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-flowlayout:$accompanistVersion")
    
    // Coil
    implementation("io.coil-kt:coil-compose:2.2.2")

    // KTOR
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