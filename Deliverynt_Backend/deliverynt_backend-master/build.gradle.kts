import org.gradle.internal.impldep.com.fasterxml.jackson.core.JsonPointer.compile
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.5"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
}

group = "PAE"
version = "0.0.1-RELEASE"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-mustache")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("com.google.code.gson:gson:2.10")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
	implementation("org.json:json:20211205")
	implementation("com.squareup.okhttp3:okhttp:4.10.0")
	implementation ("com.sendgrid:sendgrid-java:4.0.1")
	implementation("com.mailjet:mailjet-client:5.2.1")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.jar {
	manifest.attributes["Main-Class"] = "PAE.deliverynt_backend.DeliveryntBackendApplicationKt"
	val dependencies = configurations
		.runtimeClasspath
		.get()
		.map(::zipTree) // OR .map { zipTree(it) }
	from(dependencies)
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}



