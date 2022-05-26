pluginManagement {
    fun getKotlinVersion(): String {
        val versions = File("$rootDir/gradle.properties").inputStream().use {
            java.util.Properties().apply { load(it) }
        }
        return versions.getProperty("kotlinVersion")
    }

    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
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
include(":backend")
include(":androidApp")
include(":androidAppCompose")
include(":web-compose")