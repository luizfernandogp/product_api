plugins {
    java
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
    jacoco
}

group = "com.luizgomes"
version = "1.0.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

jacoco {
    toolVersion = "0.8.12" // Use a versão adequada do JaCoCo
}

group = "com.luizgomes"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
    implementation("com.github.java-json-tools:json-patch:1.13")
    implementation("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    implementation("org.mapstruct:mapstruct:1.5.5.Final")

    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testImplementation("com.intuit.karate:karate-junit5:1.4.0")
    testImplementation("com.intuit.karate:karate-netty:0.9.2")
    testImplementation("com.h2database:h2")

}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // Gera o relatório JaCoCo após os testes
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // Garante que o relatório é gerado após os testes
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}