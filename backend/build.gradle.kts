plugins {
    application
    kotlin("jvm")
//    id("kotlin-platform-jvm")
    id("com.squareup.sqldelight")
}

group = "com.example.kmmexample.backend"
version = "0.0.1"
application {
    mainClass.set("com.example.kmmexample.backend.AppKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://kotlin.bintray.com/kotlin-js-wrappers") }
}

dependencies {
    implementation(project(":shared"))

    val ktorVersion: String by project
    val koinVersion: String by project

    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-gson:$ktorVersion")

    implementation("io.insert-koin:koin-ktor:$koinVersion")
    implementation("io.insert-koin:koin-core:$koinVersion")

    //testImplementation("io.ktor:ktor-server-tests-jvm:$koinVersion")
    //testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$koinVersion")
    //testImplementation("io.insert-koin:koin-test:$koinVersion")
}

sqldelight {
    database("ServerDatabase") { // This will be the name of the generated database class.
        packageName = "com.example.kmmexample.backend"
        dialect = "mysql"
    }
}
