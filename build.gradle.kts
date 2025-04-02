import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	alias(libs.plugins.kotlinJvm)
	id("maven-publish")
}

extra["projectVersion"] = getEnv("VERSION", "latest")
extra["mavenName"] = getEnv("MAVEN_NAME")
extra["mavenSecret"] = getEnv("MAVEN_SECRET")

group = "pl.jwizard"
version = getProperty("projectVersion")

// only for java classes
java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
	compilerOptions {
		jvmTarget.set(JvmTarget.JVM_17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// this dependencies should be visible in related repositories
	api(libs.dotEnv)
	api(libs.hikariCp)
	api(libs.jacksonDatabind)
	api(libs.jacksonDataformatYaml)
	api(libs.jacksonKotlin)
	api(libs.jacksonJsr310)
	api(libs.javalin)
	api(libs.jdbi)
	api(libs.jdbiKotlin)
	api(libs.jeeAnnotationApi)
	api(libs.mysqlConnector)
	api(libs.springContext)
	api(libs.springRabbitMq)
	api(libs.springVault)

	// these dependencies are used only in this library
	implementation(libs.commonsVfs)
	implementation(libs.kotlin)
	implementation(libs.kotlinReflect)
	implementation(libs.slf4jApi)
}

tasks {
	register<Jar>("sourcesJar") {
		archiveClassifier.set("sources")
		from(sourceSets.main.get().allSource)
	}
}

publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			groupId = group.toString()
			artifactId = "jwizard-lib"
			version = project.version.toString()

			from(components["java"])
			artifact(tasks["sourcesJar"])

			pom {
				name.set("JWizard library")
				url.set("https://jwizard.pl")
				licenses {
					license {
						name.set("The AGPL-3.0 License")
						url.set("https://www.gnu.org/licenses/agpl-3.0.en.html")
					}
				}
				developers {
					developer {
						id.set("milosz08")
						name.set("Miłosz Gilga")
						email.set("miloszgilga@gmail.com")
					}
				}
				scm {
					connection.set("scm:git:git://github.com/jwizard-bot/jwizard-lib.git")
					developerConnection.set("scm:git:ssh://github.com/jwizard-bot/jwizard-lib.git")
					url.set("https://jwizard.pl")
				}
			}
		}
	}
	repositories {
		maven {
			name = "jwizardRepository"
			url = uri("https://m2.miloszgilga.pl/private")
			credentials {
				username = getProperty("mavenName")
				password = getProperty("mavenSecret")
			}
		}
	}
}

// retrieves the value of an environment variable, with a fallback to a default value
fun getEnv(name: String, defValue: String = "") = System.getenv("JWIZARD_$name") ?: defValue

// retrieves root property
fun getProperty(name: String) = rootProject.extra[name] as String

