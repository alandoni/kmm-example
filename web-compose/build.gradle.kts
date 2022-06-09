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
    js(IR) {
        browser {
            webpackTask {
                cssSupport.enabled = true
            }

            runTask {
                cssSupport.enabled = true
            }

            /*testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }*/
        }
        binaries.executable()
    }

    sourceSets {
        val coroutinesVersion: String by project
        val jsMain by getting {
            //kotlin.srcDir("src/jsMain/kotlin")
            //resources.srcDir("src/jsMain/resources")

            dependencies {
                implementation(project(":shared"))
                implementation(project(":multiplatform-compose"))
                implementation(compose.web.core)
                implementation(compose.web.widgets)
                implementation(compose.runtime)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$coroutinesVersion")
            }
        }
    }
}

afterEvaluate {
    rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
        nodeVersion = "16.0.0"
        versions.webpackDevServer.version = "4.0.0"
        versions.webpackCli.version = "4.9.0"
    }
}

compose.desktop {
    application {
        mainClass = ""
    }
}
