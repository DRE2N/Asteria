import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    `java-library`
    `maven-publish`
    id("io.papermc.paperweight.userdev") version "1.5.3"
    id("xyz.jpenilla.run-paper") version "1.0.6"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
}

group = "de.erethon"
version = "1.0-SNAPSHOT"
description = "Plugin for custom blocks and objects using display entities"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://erethon.de/repo/")
    maven("https://repo.inventivetalent.org/content/groups/public/")
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://ci.emc.gs/nexus/content/groups/aikar/")
    maven("https://repo.aikar.co/content/groups/aikar")
    maven("https://repo.md-5.net/content/repositories/releases/")
    maven("https://hub.spigotmc.org/nexus/content/groups/public/")
    maven("https://jitpack.io")
    maven("https://hub.jeff-media.com/nexus/repository/jeff-media-public/")
}

dependencies {
    paperweight.devBundle("de.erethon.papyrus", "1.19.4-R0.1-SNAPSHOT")
    implementation("de.erethon:bedrock:1.2.5")
    implementation("com.jeff_media:CustomBlockData:2.2.0")
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }

    shadowJar {
        dependencies {
            include(dependency("de.erethon:bedrock:1.2.5"))
            include(dependency("com.jeff_media:CustomBlockData:2.2.0"))
        }
        relocate("de.erethon.bedrock", "de.erethon.asteria.bedrock")
        relocate("com.jeff_media.customblockdata", "de.erethon.asteria.customblockdata")
    }
    bukkit {
        load = BukkitPluginDescription.PluginLoadOrder.STARTUP
        main = "de.erethon.asteria.Asteria"
        apiVersion = "1.19"
        authors = listOf("Malfrador")
        commands {
            register("asteria") {
                description = "Main command for Asteria"
                aliases = listOf("as")
                permission = "asteria.cmd"
                usage = "/as help"
            }
        }
    }
}
