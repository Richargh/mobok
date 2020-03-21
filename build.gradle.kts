import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.70"
    id("org.openjfx.javafxplugin") version "0.0.8"
    id("application")
}

repositories {
    mavenCentral()
}

dependencies {
    /** Language dependencies **/
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    /** Project dependencies **/
    // none

    /** Main dependencies **/
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("org.reactfx:reactfx:1.4.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.3.5")

    /** Test dependencies **/
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testImplementation("org.assertj:assertj-core:3.15.0")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

javafx {
    version = "11"
    modules("javafx.controls")
}

application {
    mainClassName = "de.richargh.mobok.MobOkKt"
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        showExceptions = true
        showStandardStreams = true
        events("passed", "skipped", "failed")
    }
}

tasks.wrapper {
    gradleVersion = "6.0.1"
}
