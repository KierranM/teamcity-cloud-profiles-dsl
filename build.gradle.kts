import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"
}

var dslVersion = "2018.2"

group = "io.github.kierranm"
version = "1.0.0-SNAPSHOT"

repositories {
    jcenter()
    maven {
        url = uri("https://download.jetbrains.com/teamcity-repository")
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.teamcity:configs-dsl-kotlin:$dslVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}