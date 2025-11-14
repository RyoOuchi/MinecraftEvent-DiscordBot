plugins {
    idea
    application
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.example"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("net.dv8tion:JDA:5.0.0-beta.20")
    implementation("commons-cli:commons-cli:1.5.0")
}

application {
    mainClass.set("org.example.Main")
}

tasks {
    shadowJar {
        archiveBaseName.set("MinecraftBossFightBot")
        archiveVersion.set("1.0")
        archiveClassifier.set("") // Important: no "-all"
        manifest {
            attributes["Main-Class"] = "org.example.Main"
        }
    }

    build {
        dependsOn(shadowJar)
    }
}