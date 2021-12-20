plugins {
    application
    kotlin("jvm") version "1.4.31"
    id("com.justai.jaicf.jaicp-build-plugin") version "0.1.1"
    kotlin("plugin.serialization") version "1.5.30"
}

group = "com.justai.jaicf"
version = "1.0.0"

// val jaicf = "1.1.3"
val jaicf = "1.2.0"
val logback = "1.2.3"

// Main class to run application on heroku. Either JaicpPollerKt, or JaicpServerKt. Will propagate to .jar main class.
application {
    mainClassName = "com.justai.jaicf.template.connections.JaicpServerKt"
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    maven("https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
    implementation("com.just-ai.jaicf:mongo:$jaicf")
    implementation ("com.google.code.gson:gson:2.8.8")

    implementation("ch.qos.logback:logback-classic:$logback")

    // define a BOM and its version
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.9.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation ("io.github.rburgst:okhttp-digest:2.5")
    implementation ("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.+")


    implementation("com.just-ai.jaicf:core:$jaicf")
    implementation("com.just-ai.jaicf:jaicp:$jaicf")
    implementation("com.just-ai.jaicf:caila:$jaicf")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:5.6.0")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    test {
        useJUnitPlatform()
    }
    build {
        dependsOn(shadowJar)
    }
}

tasks.create("stage") {
    dependsOn("shadowJar")
}

tasks.withType<com.justai.jaicf.plugins.jaicp.build.JaicpBuild> {
    mainClassName.set(application.mainClassName)
}

