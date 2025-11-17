plugins {
    id("java")
    id("application")
}

group = "com.sonarsource"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.opencsv:opencsv:5.12.0")
    testImplementation ("org.junit.jupiter:junit-jupiter:5.10.0")
    testRuntimeOnly ("org.junit.platform:junit-platform-launcher:1.10.0")
}

tasks.test {
    useJUnitPlatform()
}

application{

    mainClass.set("com.sonarsource.cinema.Main");
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.sonarsource.cinema.Main"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveFileName.set("app.jar")
}

