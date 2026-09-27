plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
}

kotlin {
    jvm()
    android {
        namespace = "__PROJECT_PACKAGE__.shared"
        compileSdk = libs.versions.compileSdk.get().toInt()
        minSdk = libs.versions.minSdk.get().toInt()
    }
    sourceSets {
        commonMain.dependencies {
            api(project(":contracts"))
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}