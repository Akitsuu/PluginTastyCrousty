plugins {
    id("java-library")
    id("xyz.jpenilla.run-paper") version "3.1.0"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.3.build.+")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
}

// Zip reproductible : meme contenu => meme hash, pour que le zip embarque
// corresponde toujours a celui publie sur GitHub
val resourcePackZip = tasks.register<Zip>("resourcePackZip") {
    from("src/main/resourcepack")
    archiveFileName = "resourcepack.zip"
    destinationDirectory = layout.buildDirectory.dir("libs")
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}

tasks {
    runServer {
        // Configure the Minecraft version for our task.
        // This is the only required configuration besides applying the plugin.
        // Your plugin's jar (or shadowJar if present) will be used automatically.
        minecraftVersion("26.3")
        jvmArgs("-Xms2G", "-Xmx2G", "-Dcom.mojang.eula.agree=true")
    }

    processResources {
        from(resourcePackZip)
        val props = mapOf(
            "version" to version,
            "description" to project.description,
            "githubRepo" to project.property("githubRepo"),
        )
        inputs.properties(props)
        filesMatching(listOf("plugin.yml", "resourcepack.properties")) {
            expand(props)
        }
    }
}
