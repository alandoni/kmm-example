import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackOutput.Target.UMD

plugins {
    kotlin("multiplatform")
    //kotlin("js")
    id("kotlinx-serialization")
    id("com.android.library")
    id("com.squareup.sqldelight")
    id("dev.icerock.moko.kswift")
}

kotlin {
    android()

    jvm()

    //macosX64("macOS")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = false
            linkerOpts.add("-lsqlite3")
        }
    }

    js(IR) {
        browser {
            webpackTask {
                output.libraryTarget = UMD
                destinationDirectory = File(buildDir, "generated/js")
            }
        }
        binaries.executable()
    }

    sourceSets {
        val ktorVersion: String by project
        val koinVersion: String by project
        val sqlDelightVersion: String by project
        val coroutinesVersion: String by project

        val commonMain by getting {
            dependencies {
                api(project(":models"))
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

                implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")
                implementation("com.squareup.sqldelight:coroutines-extensions:$sqlDelightVersion")

                implementation("io.insert-koin:koin-core:$koinVersion")

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
                implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
                implementation("io.insert-koin:koin-core:$koinVersion")
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
            }
        }
        val androidTest by getting

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
                implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")
                implementation("io.insert-koin:koin-core:$koinVersion")
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }

        val jvmMain by getting {
            dependencies {
                //implementation("org.postgresql:postgresql:42.1.4")
                implementation("mysql:mysql-connector-java:8.0.26")
                implementation("com.squareup.sqldelight:sqlite-driver:1.5.3")
                implementation("com.squareup.sqldelight:jdbc-driver:1.5.3")
                implementation("com.zaxxer:HikariCP:5.0.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
                //implementation("io.ktor:ktor-client-cio:$ktorVersion")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$coroutinesVersion")
            }
        }
    }
}

android {
    compileSdk = 33
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.example.kmmexample.data.database"
    }
    linkSqlite = true
}

// Make it easier to use sealed classes as enum
kswift {
    install(dev.icerock.moko.kswift.plugin.feature.SealedToSwiftEnumFeature)
    install(dev.icerock.moko.kswift.plugin.feature.PlatformExtensionFunctionsFeature)
}

/*tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink>().matching {
    it.binary is org.jetbrains.kotlin.gradle.plugin.mpp.Framework
}.configureEach {
    doFirst {
        val swiftDirectory = File(destinationDir, "${binary.baseName}Swift")
        val xcodeSwiftDirectory = File(buildDir, "generated/swift")
        swiftDirectory.copyRecursively(xcodeSwiftDirectory, overwrite = true)
   }
}*/
