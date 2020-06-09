import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    id("maven-publish")
    id("org.jetbrains.dokka") version ("0.10.1")
    id("com.jfrog.bintray") version ("1.8.5")
}

val fis = FileInputStream("secret.properties")
val secrets = Properties()
secrets.load(fis)
val gitUsername: String = secrets.getProperty("git.username")
val gitPassword: String = secrets.getProperty("git.password")
val bintrayUsername: String = secrets.getProperty("bintray.username")
val bintrayPassword: String = secrets.getProperty("bintray.password")

val varPublishedGroupId = "com.brunotmgomes"
val varPublishedArtifactId = "keyboard-delegate-android"
val varLibraryName = "keyboard-delegate-android"
val varLibraryVersion = "0.1.2"
val varLibraryDescription = "Simple input method switching for Fragments"

val varSiteUrl = "https://github.com/brunotmgomes/Keyboard-Delegate-Android"

val varGitUrl = "https://github.com/brunotmgomes/Keyboard-Delegate-Android.git"
val varGitBaseUrl = "example.com/my-library.git"

val varDeveloperId = "brunotmgomes"
val varDeveloperName = "Bruno T M Gomes"
val varDeveloperEmail = "me@brunotmgomes.com"

android {
    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = varLibraryVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${rootProject.ext.get("kotlin_version")}")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.core:core-ktx:1.3.0")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}

tasks {
    dokka {
        outputFormat = "html"
        outputDirectory = "$buildDir/javadoc"
        inputs.dir("src/main/java")
    }
}

val javadocJar by tasks.creating(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.dokka)
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets["main"].java.srcDirs)
}

val publicationName = "mvnArtifacts"
afterEvaluate {
    publishing {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/brunotmgomes/keyboard-delegate-android")
                credentials {
                    username = gitUsername
                    password = gitPassword
                }
            }
        }
        publications {
            create<MavenPublication>(publicationName) {
                from(components["release"])

                groupId = varPublishedGroupId
                artifactId = varPublishedArtifactId
                version = varLibraryVersion

                artifact(javadocJar)
                artifact(sourcesJar)

                pom {
                    name.set(varLibraryName)
                    description.set(varLibraryDescription)
                    url.set(varSiteUrl)
                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set(varDeveloperId)
                            name.set(varDeveloperName)
                            email.set(varDeveloperEmail)
                        }
                    }
                    scm {
                        url.set(varGitUrl)
                    }
                }

            }
        }
    }
}

bintray {
    user = bintrayUsername
    key = bintrayPassword
    setPublications(publicationName)
    publish = true
    pkg(delegateClosureOf<com.jfrog.bintray.gradle.BintrayExtension.PackageConfig> {
        repo = "keyboard-delegate-android"
        name = varLibraryName
        version = VersionConfig().apply {
            name = varLibraryVersion
        }
        websiteUrl = "http://brunotmgomes.com"
        githubRepo = "brunotmgomes/Keyboard-Delegate-Android"
        vcsUrl = "https://github.com/brunotmgomes/Keyboard-Delegate-Android"
        description = varLibraryDescription
        setLabels("kotlin")
        setLicenses("The Apache License, Version 2.0")
        desc = description
    })
}

