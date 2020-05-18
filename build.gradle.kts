plugins {
    kotlin("jvm") version "1.3.70"
    id("io.gitlab.arturbosch.detekt") version "1.6.0"
    id("org.openjfx.javafxplugin") version "0.0.8"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter {
        content {
            includeGroup("org.jetbrains.kotlinx")
        }
    }
    maven("https://dl.bintray.com/mipt-npm/dataforge")
    maven("https://dl.bintray.com/mipt-npm/scientifik")
    maven("https://dl.bintray.com/kotlin/ktor/")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    implementation("org.junit.jupiter:junit-jupiter-engine:5.6.0")
    implementation("io.ktor:ktor-server-netty:1.3.1")
    implementation("io.ktor:ktor-websockets:1.3.1")


    implementation("io.ktor:ktor-client-websockets:1.3.1")
    implementation("io.ktor:ktor-client-cio:1.3.1")
    implementation("io.ktor:ktor-client-js:1.3.1")
    implementation("io.ktor:ktor-client-okhttp:1.3.1")

    implementation("ch.qos.logback:logback-classic:1.2.3")

    implementation("com.sun.xml.ws:servlet:3.0.0-M1")
    implementation("org.everit.osgi.bundles:org.everit.osgi.bundles.javax.servlet.api:3.1.0")
//    implementation("javax.servlet:javax.servlet-api:4.0.1")

    implementation("io.ktor:ktor-client-apache:1.3.1")

    implementation("io.ktor:ktor-auth:1.3.1")

    implementation("com.github.spoptchev:scientist:1.0.2")
    implementation("com.github.squirrelgrip:Scientist4K:0.3.3")
    implementation("scientifik:plotlykt-server:0.1.1")

    implementation("no.tornado:tornadofx:1.7.20")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.6.0")
}

javafx {
    modules("javafx.web")
}

detekt {
    failFast = true // fail build on any finding
    buildUponDefaultConfig = true // preconfigure defaults
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
}
