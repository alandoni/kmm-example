import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackOutput.Target.UMD

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
}

version = "0.0.1"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

kotlin {
    android()

    js(IR) {
        browser {
            webpackTask {
                cssSupport.enabled = true
                output.libraryTarget = UMD
                destinationDirectory = File(buildDir, "generated/js")
            }

            runTask {
                cssSupport.enabled = true
            }
        }
        binaries.executable()
    }

    jvm()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    macosX64("macOS")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(compose.runtime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jsMain by getting {
            //kotlin.srcDir("src/jsMain/kotlin")
            //resources.srcDir("src/jsMain/resources")
            dependsOn(commonMain)
            dependencies {
                implementation(compose.web.core)
                implementation(compose.web.widgets)
                implementation(compose.runtime)
            }
        }
        val jvmMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                val composeVersion: String by project
                implementation("androidx.compose.ui:ui:$composeVersion")
                implementation("androidx.compose.material3:material3:1.0.0-alpha12")
                implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
            }
        }
    }
}

android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
}
