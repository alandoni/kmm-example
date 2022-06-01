pluginManagement {
    fun getVersion(key: String): String {
        val versions = File("$rootDir/gradle.properties").inputStream().use {
            java.util.Properties().apply { load(it) }
        }
        return versions.getProperty(key)
    }

    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("multiplatform").version(getVersion("kotlinVersion"))
        id("org.jetbrains.compose").version(getVersion("composePluginVersion"))
    }

    resolutionStrategy {
        eachPlugin {
            /*if (requested.id.id.startsWith("org.jetbrains.kotlin")) {
                useVersion(getKotlinVersion())
            }*/
        }
    }
}

rootProject.name = "kmmexample"

include(":models")
include(":shared")
include(":multiplatform-compose")
include(":backend")
include(":androidApp")
include(":androidAppCompose")
include(":web-compose")
include(":desktop-compose")
include(":ios-compose")
