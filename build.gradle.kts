plugins {
    java
    `maven-publish`
    id("com.google.cloud.tools.jib") version("3.0.0")
    id("org.springframework.boot") version("2.5.0")
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://emily.dreamexposure.org/artifactory/dreamexposure-public/")
    }
    maven {
        url = uri("https://repo.spring.io/libs-release")
    }
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    implementation("org.thymeleaf:thymeleaf:3.0.12.RELEASE")
    implementation("org.thymeleaf:thymeleaf-spring5:3.0.12.RELEASE")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:2.5.3")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:2.5.0")
    implementation("org.springframework.session:spring-session:1.3.5.RELEASE")
    implementation("org.springframework.security:spring-security-core:5.5.0")
    implementation("org.springframework.boot:spring-boot-starter-webflux:2.5.0")
}

group = "org.dreamexposure"
version = "1.0.1"
description = "DreamExposure-Website"
java.sourceCompatibility = JavaVersion.VERSION_1_8

jib {
    to.image = "rg.nl-ams.scw.cloud/dreamexposure/dreamexposure-site:$version"
    container {
        creationTime = "USE_CURRENT_TIMESTAMP"
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
