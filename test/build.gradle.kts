repositories {
    mavenCentral()
}

plugins {
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.serialization") version "2.0.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

dependencies {
    testImplementation(kotlin("test"))
    testCompileOnly("org.testng:testng:7.9.0")
    testImplementation("io.appium:java-client:9.3.0")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.23.0")
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(platform("io.qameta.allure:allure-bom:2.26.0"))
    testImplementation("io.qameta.allure:allure-testng")
    testImplementation(platform("org.jetbrains.kotlin:kotlin-bom:2.0.20"))
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib")
}

tasks.test {
    useTestNG {
        suiteXmlFiles = listOf(File("src/test/resources/testng.xml"))
    }
}

kotlin {
    jvmToolchain(20)
}
