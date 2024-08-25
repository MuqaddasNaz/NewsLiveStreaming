pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        jcenter()
        maven { url = uri("https://www.jitpack.io" ) }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        jcenter()
        maven { url = uri("https://www.jitpack.io" ) }
    }
}

rootProject.name = "News Live Streaming"
include(":app")
