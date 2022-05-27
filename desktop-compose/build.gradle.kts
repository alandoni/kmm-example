import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.compose.compose

plugins {
    application
    kotlin("jvm")
    id("org.jetbrains.compose")
}

group = "com.example.kmmexample.desktop"
version = "1.0"
application {
    mainClass.set("com.example.kmmexample.desktop.MainKt")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}

dependencies {
    val koinVersion: String by project
    val coroutinesVersion: String by project

    implementation(project(":shared"))
    implementation(compose.desktop.currentOs)
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
