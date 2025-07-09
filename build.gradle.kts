plugins {
    alias(libs.plugins.android.application) apply false
    // Removed library plugin since you don't use it
}

buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
