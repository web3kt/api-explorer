import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

group = "org.web3kt"
version = "0.2.1"

plugins {
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("plugin.jpa") version "2.0.0"
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.spring") version "2.0.0"
    kotlin("kapt") version "2.0.0"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

springBoot {
    buildInfo()
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // spring boot
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-quartz")
    implementation("org.springframework.boot:spring-boot-starter-web")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // database
    runtimeOnly("com.mysql:mysql-connector-j")

    // querydsl
    implementation("com.querydsl:querydsl-core:5.1.0")
    implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.1.0:jakarta")

    // spring doc
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    // web3Kt
    implementation("org.web3kt:core:0.2.1")

    implementation("p6spy:p6spy:3.9.1")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<BootBuildImage> {
    val registryUrl = "ghcr.io"
    val projectName = "web3kt"
    val username = System.getenv("USERNAME")
    val password = System.getenv("PASSWORD")

    val imageName = "$registryUrl/$projectName/${project.name}"
    this.imageName.set(imageName)
    this.tags.add("$imageName:${project.version}")
    this.publish.set(true)

    docker {
        publishRegistry {
            this.url.set(registryUrl)
            this.username.set(username)
            this.password.set(password)
        }
    }
}
