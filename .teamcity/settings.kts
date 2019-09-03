import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildFeatures.PullRequests
import jetbrains.buildServer.configs.kotlin.v2018_2.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.v2018_2.buildFeatures.pullRequests
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs

version = "2019.1"

project {
    params {
        param("teamcity.ui.settings.readOnly", "true")
        param("env.BINTRAY_USERNAME", "kierranm")
        password("env.BINTRAY_PASSWORD", "credentialsJSON:f399a576-3371-4f8a-8959-c2baaa21513a")
    }
    buildType(Build)
    buildType(Publish)
}

object Build : BuildType({
    name = "Build"
    description = "Build the DSL"

    // For build Widget
    allowExternalStatus = true

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        maven {
            goals = "clean compile"
        }
    }

    triggers {
        vcs {
            branchFilter = """
                +:<default>
                +:pull/*
            """.trimIndent()
        }
    }

    features {
        commitStatusPublisher {
            vcsRootExtId = "${DslContext.settingsRoot.id}"
            publisher = github {
                githubUrl = "https://api.github.com"
                authType = personalToken {
                    token = "%github.commit_status_publisher_token%"
                }
            }
        }
        pullRequests {
            vcsRootExtId = "${DslContext.settingsRoot.id}"
            provider = github {
                authType = vcsRoot()
                filterAuthorRole = PullRequests.GitHubRoleFilter.MEMBER
            }
        }
    }
})

object Publish : BuildType({
    name = "Publish"
    description = "Publish the DSL to Bintray"

    // For build Widget
    allowExternalStatus = true

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        maven {
            goals = "deploy"
            userSettingsSelection = "bintray"
        }
    }

    triggers {
        vcs {
            branchFilter = """
                +:v*
            """.trimIndent()
        }
    }
})