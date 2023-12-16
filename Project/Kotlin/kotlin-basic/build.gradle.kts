plugins {
    kotlin("jvm") version "1.9.20"
}

group = "com.jong1.kotlinbasic"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    //coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.5")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}