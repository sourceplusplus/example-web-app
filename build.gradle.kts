plugins {
    id("org.springframework.boot") version "2.7.3"
    id("io.spring.dependency-management") version "1.0.13.RELEASE"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("java")
    id("application")
}

group = "spp.example"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("spp.example.operate.WebappOperator")
    applicationName = "example-jvm"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.apache.commons:commons-pool2:2.11.1")
    implementation("com.h2database:h2:1.4.200")
    implementation("com.github.javafaker:javafaker:1.0.2")

    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    compileOnly("org.apache.skywalking:apm-toolkit-trace:8.8.0")
    implementation("org.apache.skywalking:apm-toolkit-logback-1.x:8.12.0")
}

tasks {
    test {
        testLogging {
            events("passed", "skipped", "failed")
            setExceptionFormat("full")

            outputs.upToDateWhen { false }
            showStandardStreams = true
        }
    }
}
