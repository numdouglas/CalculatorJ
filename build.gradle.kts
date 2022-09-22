plugins {
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
}

tasks.jar {
    manifest.attributes["Main-Class"] = "calc"
    // OR another notation
    // manifest {
    //     attributes["Main-Class"] = "com.example.MyMainClass"
    // }
}
