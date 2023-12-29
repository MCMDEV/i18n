plugins {
    id("io.papermc.paperweight.userdev") version "1.5.5"
    id("xyz.jpenilla.run-paper") version "2.2.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    maven("https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
    implementation(project(":i18n-adventure"))
    implementation(project(":i18n-common"))
    compileOnly("com.comphenix.protocol:ProtocolLib:5.2.0-SNAPSHOT")
}

bukkit {
    main = "de.helixdevs.i18n.paper.I18nPaperPlugin"
    apiVersion = "1.20"

    depend = listOf("ProtocolLib")
    commands {
        register("reloadtranslations") {
            description = "Reloads all translations"
            permission = "i18n.reloadtranslations"
        }
    }
}

tasks {
    assemble {
        dependsOn("reobfJar", "shadowJar")
    }
}