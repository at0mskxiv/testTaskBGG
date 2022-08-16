plugins {
    java
}
dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.0"))
    testImplementation(platform("io.cucumber:cucumber-bom:7.6.0"))

    testImplementation("io.cucumber:cucumber-java")
    testImplementation("io.cucumber:cucumber-junit-platform-engine")
    testImplementation("org.junit.platform:junit-platform-suite")
    testImplementation("org.junit.jupiter:junit-jupiter")
    // https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java
    implementation("org.seleniumhq.selenium:selenium-java:4.4.0")
    // https://mvnrepository.com/artifact/io.rest-assured/rest-assured
    testImplementation("io.rest-assured:rest-assured:5.1.1")
    testImplementation("io.rest-assured:json-path:5.1.1")
    testImplementation("io.rest-assured:xml-path:5.1.1")
    testImplementation("io.rest-assured:json-schema-validator:5.1.1")
    // https://mvnrepository.com/artifact/com.thoughtworks.xstream/xstream
    implementation("com.thoughtworks.xstream:xstream:1.4.19")

}

repositories {
    mavenLocal()
    mavenCentral()
}

tasks.withType<Test> {
    useJUnitPlatform()
    // Work around. Gradle does not include enough information to disambiguate
    // between different examples and scenarios.
    systemProperty("cucumber.junit-platform.naming-strategy", "long")
}
