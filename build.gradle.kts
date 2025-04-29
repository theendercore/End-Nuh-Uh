@file:Suppress("PropertyName", "VariableNaming")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.fabric.loom)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.iridium)
    alias(libs.plugins.iridium.publish)
    alias(libs.plugins.iridium.upload)
}

repositories {
    maven("https://teamvoided.org/releases")
    maven("https://maven.terraformersmc.com/") { name = "Terraformers" }
    mavenCentral()
}

println("Task: " + gradle.startParameter.taskNames.joinToString(","))

modSettings {
    entrypoint("main", "org.teamvoided.endnuhuh.EndNuhUh::init")
    mixinFile("${modId()}.mixins.json")
//    entrypoint("client", "org.teamvoided.endnuuh.TemplateClient::init")
//    entrypoint("fabric-datagen", "org.teamvoided.endnuuh.data.gen.TemplateData")

//    mixinFile("${modId()}.client.mixins.json")
}

dependencies {
    modImplementation(fileTree("libs"))
    modImplementation(libs.modmenu)
}

loom {
    splitEnvironmentSourceSets()
    runs {
        create("DataGen") {
            client()
            ideConfigGenerated(true)
            vmArg("-Dfabric-api.datagen")
            vmArg("-Dfabric-api.datagen.output-dir=${file("src/main/generated")}")
            vmArg("-Dfabric-api.datagen.modid=${modSettings.modId()}")
            runDir("build/datagen")
        }

        create("TestWorld") {
            client()
            ideConfigGenerated(true)
            runDir("run")
            programArgs("--quickPlaySingleplayer", "test", "--username", "vDev")
        }
    }
}

sourceSets["main"].resources.srcDir("src/main/generated")

tasks {
    val targetJavaVersion = 21
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(targetJavaVersion)
    }

    withType<KotlinCompile>().all {
        compilerOptions.jvmTarget = JvmTarget.JVM_21
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(JavaVersion.toVersion(targetJavaVersion).toString()))
        withSourcesJar()
    }
}

publishScript {
    releaseRepository("TeamVoided", "https://maven.teamvoided.org/releases")
    publication(modSettings.modId(), false)
    publishSources(true)
}

uploadConfig {
//    debugMode = true
    modrinthId = "Q2qswmhx"
    curseId = "1254692"
    changeLog = File("./changelog.md").readText()

    // FabricApi
    modrinthDependency("P7dR8mSH", uploadConfig.REQUIRED)
    curseDependency("fabric-api", uploadConfig.REQUIRED)
    // Fabric Language Kotlin
    modrinthDependency("Ha28R6CL", uploadConfig.REQUIRED)
    curseDependency("fabric-language-kotlin", uploadConfig.REQUIRED)
}
