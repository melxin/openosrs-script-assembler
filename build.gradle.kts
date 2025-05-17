/*
 * Copyright (c) 2022, Owain van Brakel <https://github.com/Owain94>
 * All rights reserved.
 *
 * This code is licensed under GPL3, see the complete license in
 * the LICENSE file in the root directory of this source tree.
 */
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-gradle-plugin")
    kotlin("jvm") version "1.7.0"
    `maven-publish`
}

val rlver = "1.11.9-SNAPSHOT"

group = "com.openosrs"
version = "1.0.4"

repositories {
    mavenCentral()
    mavenLocal()
    maven {
        url = uri("https://raw.githubusercontent.com/open-osrs/hosting/master")
    }

    exclusiveContent {
        forRepository {
            maven {
                url = uri("https://repo.runelite.net")
            }
        }
        filter {
            includeModule("net.runelite", "runelite-parent")
            includeModule("net.runelite", "cache")
        }
    }
}

dependencies {
    annotationProcessor(group = "org.projectlombok", name = "lombok", version = "1.18.20")
    compileOnly(group = "org.projectlombok", name = "lombok", version = "1.18.20")

    implementation(group = "com.google.guava", name = "guava", version = "30.1.1-jre")
    implementation(group = "org.antlr", name = "antlr4-runtime", version = "4.13.1")
    implementation(group = "net.runelite", name = "cache", version = rlver) {
        isTransitive = false
    }

    implementation(group = "org.tomlj", name = "tomlj", version = "1.1.0")
    implementation(group = "com.squareup", name = "javapoet", version = "1.13.0")
}

gradlePlugin {
    plugins {
        create("scriptAssemblerPlugin") {
            id = "com.openosrs.scriptassembler"
            implementationClass = "com.openosrs.scriptassembler.ScriptAssemblerPlugin"
        }
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

publishing {
    repositories {
        maven {
            url = uri("$buildDir/repo")
        }
        if (System.getenv("REPO_URL") != null) {
            maven {
                url = uri(System.getenv("REPO_URL"))
                credentials {
                    username = System.getenv("REPO_USERNAME")
                    password = System.getenv("REPO_PASSWORD")
                }
            }
        }
    }
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}
