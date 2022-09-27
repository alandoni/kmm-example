buildscript {
    val kotlinVersion: String by project
    val sqlDelightVersion: String by project

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        mavenLocal()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
        classpath("com.android.tools.build:gradle:7.2.2")
        classpath("com.squareup.sqldelight:gradle-plugin:$sqlDelightVersion")
        classpath("dev.icerock.moko:kswift-gradle-plugin:0.5.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

/*tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}*/