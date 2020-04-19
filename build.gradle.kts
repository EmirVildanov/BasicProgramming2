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
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    implementation("org.junit.jupiter:junit-jupiter-engine:5.6.0")
//    implementation("de.jensd:fontawesomefx-weathericons:2.0.10-9.1.2")
    implementation("no.tornado:tornadofx:1.7.20")

//    compile("org.codehaus.griffon:griffon-javafx:14")
//    compile("no.tornado:tornadofx:1.7.17")
//    implementation("org.controlsfx:controlsf.x:11.0.1")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.6.0")
}

javafx {
    version = "14"
    modules("javafx.controls")
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
