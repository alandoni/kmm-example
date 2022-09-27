plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

kotlin {
    js(IR) { //run with ./gradlew web-compose:jsBrowserDevelopmentRun --continuous
        browser {
            webpackTask {
                cssSupport.enabled = true
            }

            runTask {
                cssSupport.enabled = true
            }

            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.web.core)
                implementation(compose.runtime)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.6.1")
            }
        }
    }
}
