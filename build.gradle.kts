// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val kotlinVersion = "1.3.72"
    extra.set("kotlin_version", kotlinVersion)
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
