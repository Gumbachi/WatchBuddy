import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties


plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization") version "1.8.0"
    id("io.realm.kotlin") version "1.6.1"
    id("com.apollographql.apollo3") version "3.7.5"
}

android {
    namespace = "com.gumbachi.watchbuddy"
    compileSdk = 33
    buildFeatures.buildConfig = true

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
        buildConfigField(
            "String",
            "TMDB_KEY",
            gradleLocalProperties(rootDir).getProperty("TMDB_KEY")
        )
        buildConfigField(
            "int",
            "ANILIST_CLIENT_ID",
            gradleLocalProperties(rootDir).getProperty("ANILIST_CLIENT_ID")
        )
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
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
val accompanistVersion = "0.30.0"
val ktorVersion = "2.2.4"
val koinAndroidVersion = "3.4.3"
val apolloVersion = "3.7.5"
val realmVersion = "1.7.0"


dependencies {


    // Compose Dependencies
    val composeBOM = platform("androidx.compose:compose-bom:2023.01.00")
    implementation("androidx.compose:compose-bom:2023.03.00")
    testImplementation("androidx.compose:compose-bom:2023.03.00")
    implementation("androidx.compose.material3:material3:1.1.0-beta01")

    // Icons
    implementation("androidx.compose.material:material-icons-extended:1.5.0-alpha01")

    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0-alpha01")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.0-alpha01")

    // Koin
    implementation("io.insert-koin:koin-androidx-compose:$koinAndroidVersion")

    // Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-pager:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-pager-indicators:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-flowlayout:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-webview:$accompanistVersion")


    // Coil
    implementation("io.coil-kt:coil-compose:2.3.0")

    // KTOR
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    // Kotlinx Libraries
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // MongoDB Realm
    implementation("io.realm.kotlin:library-sync:$realmVersion")

    // Apollo GraphQL
    implementation("com.apollographql.apollo3:apollo-runtime:$apolloVersion")
}

apollo {
    service("service") {
        packageName.set("com.gumbachi.watchbuddy")
    }
}