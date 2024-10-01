plugins {
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.spring") version "2.0.20"
    kotlin("plugin.serialization") version "2.0.20"

    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.graalvm.buildtools.native") version "0.10.2"
}

group = "de.codecentric"
version = "1.0.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["springModulithVersion"] = "1.2.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude("org.springframework.boot", "spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-docker-compose")

    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("commons-io:commons-io:2.17.0")

    val kotlinXmlBuilder = "1.9.1"
    implementation("org.redundent:kotlin-xml-builder:$kotlinXmlBuilder")

    // Kotlin Logging
    val kotlinLoggingVersion = "5.1.0"
    implementation("io.github.oshai:kotlin-logging-jvm:$kotlinLoggingVersion")

    // Serialization
    val kotlinxDatetimeVersion = "0.6.1"
    val kotlinxSerializationVersion = "1.7.3"
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$kotlinxDatetimeVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")

    // Spring Modulith
//    implementation("org.springframework.modulith:spring-modulith-actuator")
//    implementation("org.springframework.modulith:spring-modulith-starter-core")
//    implementation("org.springframework.modulith:spring-modulith-observability")
//    implementation("org.springframework.modulith:spring-modulith-starter-insight")

    implementation("io.micrometer:micrometer-tracing-bridge-brave")
    implementation("io.zipkin.reporter2:zipkin-reporter-brave")

    // Kotest
    val kotestVersion = "5.9.1"
    val mockkVersion = "1.13.12"
    val springMockkVersion = "4.0.2"
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("com.ninja-squad:springmockk:$springMockkVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.modulith:spring-modulith-starter-test")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.modulith:spring-modulith-bom:${property("springModulithVersion")}")
    }
}

springBoot {
    buildInfo()
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named("processTestAot") {
    // NOTE: Currently required to enable @MockkBean support in tests
    enabled = false
}