import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.1.5.RELEASE"
	id("io.spring.dependency-management") version "1.0.7.RELEASE"
	id("org.jetbrains.kotlin.plugin.jpa") version "1.2.71"
	kotlin("jvm") version "1.2.71"
	kotlin("plugin.spring") version "1.2.71"
}

group = "SpringMicroservicesInAction"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "Greenwich.SR1"

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web:2.1.5.RELEASE")

	implementation("org.springframework.cloud:spring-cloud-config-client:2.1.2.RELEASE")
	implementation("org.springframework.cloud:spring-cloud-sleuth-core:2.1.2.RELEASE")
	implementation("org.springframework.cloud:spring-cloud-sleuth-zipkin:2.1.2.RELEASE")
	implementation("org.springframework.cloud:spring-cloud-starter-config")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:2.1.1.RELEASE")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-hystrix:2.1.1.RELEASE")
	implementation("org.springframework.cloud:spring-cloud-starter-sleuth:2.1.2.RELEASE")
	implementation("org.springframework.cloud:spring-cloud-starter-stream-kafka:2.1.1.RELEASE")
	implementation("org.springframework.cloud:spring-cloud-stream:2.1.1.RELEASE")

	implementation("org.springframework.security:spring-security-rsa:1.0.8.RELEASE")
	
	implementation("org.postgresql:postgresql:42.2.5")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
	languageVersion = "1.3"
}