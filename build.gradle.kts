plugins {
    java
    id("com.gradleup.shadow") version "9.2.2"
}

group = "me.datatags"
version = "1.6.1"

repositories {
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "placeholderapi"
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }
    maven {
        name = "tcoded-releases"
        url = uri("https://repo.tcoded.com/releases")
    }
    mavenCentral()
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.8-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.6")

    // FoliaLib (implementation because it needs to be shaded)
    implementation("com.tcoded:FoliaLib:0.5.1")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.shadowJar {
    archiveBaseName.set("StatisticEditor")
    archiveVersion.set(project.version.toString())

    relocate("com.tcoded.folialib", "me.datatags.statisticeditor.libs.folialib")
}

tasks.jar {
    enabled = false
}

tasks.build {
    dependsOn(tasks.shadowJar)
}
