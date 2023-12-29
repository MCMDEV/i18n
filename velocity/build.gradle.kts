plugins {
    id("xyz.jpenilla.run-velocity") version "2.2.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.2.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.2.0-SNAPSHOT")

    implementation(project(":i18n-adventure"))
    implementation(project(":i18n-common"))
}

tasks {
    assemble {
        dependsOn("shadowJar")
    }
    runVelocity {
        velocityVersion("3.2.0-SNAPSHOT")
    }
}