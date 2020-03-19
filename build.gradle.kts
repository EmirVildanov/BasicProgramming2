plugins {
    kotlin("jvm") version "1.3.70"
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
}

