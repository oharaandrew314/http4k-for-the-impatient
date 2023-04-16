plugins {
    kotlin("jvm") version "1.8.20"
    kotlin("plugin.spring") version "1.8.20"
    id("org.springframework.boot") version "2.7.8"
    id("io.spring.dependency-management") version "1.1.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:4.41.4.0"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-contract")
    implementation("org.http4k:http4k-server-undertow")
    implementation("org.http4k:http4k-server-jetty")
    implementation("org.http4k:http4k-format-moshi")
    implementation("org.http4k:http4k-format-jackson")
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.5.5")
}

kotlin {
    jvmToolchain(17)
}