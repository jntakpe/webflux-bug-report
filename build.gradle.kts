import org.asciidoctor.gradle.AsciidoctorTask
import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.dsl.SpringBootExtension

val commonsVersion = "0.4.0-SNAPSHOT"
val kotlinVersion = "1.1.60"
val springBootVersion = "2.0.0.M6"
val assertJVersion = "3.8.0"
val commonsLang3Version = "3.6"
val springCloudVersion by extra { "Finchley.M3" }
val snippetsDir by extra { file("build/generated-snippets") }


extra["spring-restdocs.version"] = "2.0.0.BUILD-SNAPSHOT"
extra["kotlin.version"] = kotlinVersion
extra["spring.version"] = "5.0.2.BUILD-SNAPSHOT"

group = "com.github.jntakpe"
version = "1.0.0"

buildscript {
    repositories {
        jcenter()
        maven("https://repo.spring.io/snapshot")
        maven("https://repo.spring.io/milestone")
    }
    dependencies {
        val springBootVersion = "2.0.0.M6"
        val junitGradleVersion = "1.0.1"
        classpath("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
        classpath("org.junit.platform:junit-platform-gradle-plugin:$junitGradleVersion")
    }
}

plugins {
    val kotlinVersion = "1.1.60"
    val springIOVersion = "1.0.3.RELEASE"
    val asciiDocVersion = "1.5.3"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    id("io.spring.dependency-management") version springIOVersion
    id("org.asciidoctor.convert") version asciiDocVersion
}

apply {
    plugin("org.springframework.boot")
    plugin("org.junit.platform.gradle.plugin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    compile(kotlin("stdlib-jre8", kotlinVersion))
    compile(kotlin("reflect", kotlinVersion))
    compile("org.springframework.boot:spring-boot-starter-webflux")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    testCompile("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
    }
    testCompile("org.springframework.restdocs:spring-restdocs-webtestclient")
    testCompile("io.projectreactor:reactor-test")
    testCompile("org.junit.jupiter:junit-jupiter-api")
    testCompile("org.springframework.restdocs:spring-restdocs-webtestclient")
    testRuntime("org.junit.jupiter:junit-jupiter-engine")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-starter-parent:$springBootVersion")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

repositories {
    jcenter()
    maven("https://repo.spring.io/snapshot")
    maven("https://repo.spring.io/milestone")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
}