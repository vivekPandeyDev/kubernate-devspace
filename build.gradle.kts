
plugins {
	java
	id("org.springframework.boot") version "3.5.7"
	id("io.spring.dependency-management") version "1.1.7"
    id("checkstyle")
    id("pmd")
    id("jacoco")
    id("com.github.spotbugs") version "5.0.13"
    id("io.freefair.lombok") version "9.0.0"
}

group = "com.alex"
version = "1.0.0"
description = "Demo project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}


dependencies {
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	developmentOnly("org.springframework.boot:spring-boot-docker-compose")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

checkstyle {
    toolVersion = "10.12.0"
    configFile = file("${project.rootDir}/config/checkstyle/checkstyle.xml")
}

tasks.register<Checkstyle>("checkstyleReport") {
    group = "verification"
    description = "Generates HTML Checkstyle report"
    source = fileTree("src/main/java")
    classpath = files()
    configFile = file("${project.rootDir}/config/checkstyle/checkstyle.xml")
    reports {
        html.required.set(true)
        html.outputLocation.set(file("${project.rootDir}/reports/checkstyle/checkstyle.html"))
    }
}


pmd {
    toolVersion = "7.18.0"
    ruleSets = listOf("${project.rootDir}/config/pmd/pmd-ruleset.xml")
    isIgnoreFailures = false
}

tasks.withType<Pmd> {
    reports {
        xml.required.set(true)
        html.required.set(true)
        html.outputLocation.set(file("${project.rootDir}/reports/pmd/${name}.html"))
    }
}


jacoco {
    toolVersion = "0.8.12"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}

spotbugs {
    toolVersion.set("4.9.8")
    ignoreFailures.set(false)
    showProgress.set(true)
    reportsDir.set(layout.buildDirectory.dir("spotbugsReports"))

}