plugins {
    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.1.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("com.avast.gradle.docker-compose") version "0.16.12"
    id("com.ryandens.javaagent-application") version "0.5.0"
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
    maven(url = "https://pkg.sourceplus.plus/sourceplusplus/probe-jvm")
}

dependencies {
    javaagent("plus.sourceplus.probe:probe-jvm:0.7.7.1")

    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.apache.commons:commons-pool2:2.11.1")
    implementation("com.h2database:h2:1.4.200")
    implementation("com.github.javafaker:javafaker:1.0.2") {
        exclude("org.yaml", "snakeyaml")
    }

    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")

    implementation("org.apache.skywalking:apm-toolkit-trace:8.15.0")
    implementation("org.apache.skywalking:apm-toolkit-logback-1.x:8.14.0")
}

tasks.getByName("composeUp") {
    dependsOn("installDist")
    mustRunAfter("installDist")
}
tasks.register("assembleUp") {
    dependsOn("assemble", "composeUp")
}
