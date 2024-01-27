plugins {
    kotlin("jvm")
}

kotlin {
    jvmToolchain(21)
}

allprojects {
    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
    }
}

subprojects {
    dependencies {
        implementation(platform(Http4k.bom))
        implementation(platform("org.http4k:http4k-connect-bom:_"))
        

        testImplementation(kotlin("test"))
        testImplementation("io.kotest:kotest-assertions-core-jvm:_")
    }

    tasks.test {
        useJUnitPlatform()
    }
}