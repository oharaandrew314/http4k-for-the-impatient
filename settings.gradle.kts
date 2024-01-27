rootProject.name = "http4k for the impatient"

plugins {
    id("de.fayard.refreshVersions").version("0.60.3")
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

include("http4k")
include("spring")
include("dynamodb")
