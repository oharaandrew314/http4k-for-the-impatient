dependencies {
    implementation(Http4k.core)
    implementation(Http4k.contract)
    implementation(Http4k.server.undertow)
    implementation(Http4k.server.jetty)
    implementation(Http4k.format.moshi)
    implementation(Http4k.format.jackson)
}