plugins {
    application
    kotlin("jvm") version "1.4.31"
    id("com.justai.jaicf.jaicp-build-plugin") version "0.1.1"
    kotlin("plugin.serialization") version "1.5.30"
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

group = "com.justai.jaicf"
version = "1.0.0"

// val jaicf = "1.1.3"
val jaicf = "1.2.2"
val logback = "1.2.3"
val nlpVersion = "1.0.0"

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
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.2")
    //implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
    implementation("com.just-ai.jaicf:mongo:$jaicf")
    implementation ("com.google.code.gson:gson:2.8.9")

    implementation("ch.qos.logback:logback-classic:$logback")

    // define a BOM and its version
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.8.0"))
    implementation("com.squareup.okhttp3:okhttp:4.8.0")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation ("io.github.rburgst:okhttp-digest:2.6")
    implementation ("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")


    implementation("com.just-ai.jaicf:core:$jaicf")
    implementation("com.just-ai.jaicf:jaicp:$jaicf")
    implementation("com.just-ai.jaicf:caila:$jaicf")
    implementation("com.just-ai.jaicf:telegram:$jaicf")
    implementation ("com.londogard:londogard-nlp-toolkit:$nlpVersion")

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

tasks.withType<Jar> {
    manifest {
        attributes(
            mapOf(
                "Main-Class" to application.mainClassName
            )
        )
    }
}

tasks.withType<com.justai.jaicf.plugins.jaicp.build.JaicpBuild> {
    mainClassName.set(application.mainClassName)
}

