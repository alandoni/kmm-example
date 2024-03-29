import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.compose.experimental.dsl.IOSDevices

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

kotlin {
    listOf(
        iosX64("uikitX64"),
        iosArm64("uikitArm64"),
        iosSimulatorArm64("uikitSimArm64"),
    ).forEach {
        it.binaries.framework {
            isStatic = false
            linkerOpts.add("-lsqlite3")
        }
        it.binaries.executable {
            linkerOpts.add("-lsqlite3")
            entryPoint = "com.example.kmmexample.ios.main"
            freeCompilerArgs += listOf(
                "-linker-option", "-framework", "-linker-option", "Metal",
                "-linker-option", "-framework", "-linker-option", "CoreText",
                "-linker-option", "-framework", "-linker-option", "CoreGraphics"
            )
            // TODO: the current compose binary surprises LLVM, so disable checks for now.
            freeCompilerArgs += "-Xdisable-phases=VerifyBitcode"
        }
    }

    sourceSets {
        val commonMain by getting {
            //kotlin.srcDir("src/commonMain/kotlin")
            //resources.srcDir("src/commonMain/resources")

            dependencies {
                implementation(project(":shared"))
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.runtime)
                //implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.6.1")
            }
        }
    }
}

compose.experimental {
    web.application { }
    uikit.application {
        bundleIdPrefix = "com.example"
        projectName = "kmmexample"
        deployConfigurations {
            simulator("IPhone") {
                //Usage: ./gradlew iosDeployIPhoneDebug
                device = IOSDevices.IPHONE_13
            }
            simulator("IPad") {
                //Usage: ./gradlew iosDeployIPadDebug
                device = IOSDevices.IPAD_MINI_6th_Gen
            }
            connectedDevice("Device") {
                //Usage: ./gradlew iosDeployDeviceRelease
                this.teamId = "***"
            }
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

kotlin {
    targets.withType<KotlinNativeTarget> {
        binaries.all {
            // TODO: the current compose binary surprises LLVM, so disable checks for now.
            freeCompilerArgs += "-Xdisable-phases=VerifyBitcode"
        }
    }
}

// TODO: remove when https://youtrack.jetbrains.com/issue/KT-50778 fixed
project.tasks.withType(org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile::class.java).configureEach {
    kotlinOptions.freeCompilerArgs += listOf(
        "-Xir-dce-runtime-diagnostic=log"
    )
}