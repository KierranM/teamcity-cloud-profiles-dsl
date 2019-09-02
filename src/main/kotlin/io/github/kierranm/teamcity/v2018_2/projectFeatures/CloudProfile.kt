package io.github.kierranm.teamcity.v2018_2.projectFeatures

import jetbrains.buildServer.configs.kotlin.v2018_2.ProjectFeature
import jetbrains.buildServer.configs.kotlin.v2018_2.ProjectFeatures
import jetbrains.buildServer.configs.kotlin.v2018_2.TeamCityDsl

@TeamCityDsl
abstract class CloudProfile() : ProjectFeature() {
    /**
     * Name of the Cloud Profile
     */
    lateinit var name: String
    /**
     * Description of the cloud profile
     */
    var description: String = ""
    /**
     * TeamCity server URL for the agents to connect to
     * Will default to the current server
     */
    var profileServerUrl: String = ""
    /**
     * Whether this profile is enabled or not
     */
    var enabled: Boolean = true
    /**
     * The type of CloudProfile this represents
     */
    lateinit var cloudCode: String
    /**
     * The total amount of work time after which an agent will be terminated
     * By default this is disabled
     */
    var terminateAfterTotalWorkTime: Int = 0
    /**
     * Whether each agent should be terminated after running a build
     * By default this is false
     */
    var terminateAfterBuild: Boolean = true
    /**
     * The number of minutes before the hour that an idle agent will be terminated
     * By default this is disabled
     */
    var terminateMinutesBeforeNextHour: Int = 0
    /**
     * The number of minutes after which an idle agent will be termianted
     * By default this is 30 minutes
     */
    var terminateAfterIdleTime: Int = 30
    /**
     * The maximum number of running agents across all images
     * By default this is infinite
     */
    var maxRunningInstances: Int = 0

    /**
     * The agent push preset name
     * By default this is empty
     */
    var agentPushPreset: String = ""

    val images = CloudImages(this)

    /**
     * Creates a cloud profile feature and initializes it with the specified init block
     * @param init initialization block
     */
    constructor(init: CloudProfile.() -> Unit) : this() {
        type = "CloudProfile"

        init()
        param("name", name)
        param("description", description)
        param("system.cloud.profile_id", id!!)
        param("profileId", id!!)
        param("enabled", enabled.toString())
        param("cloud-code", cloudCode)

        if (maxRunningInstances != 0) {
            param("max-running-instances", maxRunningInstances.toString())
        }

        if (!profileServerUrl.isEmpty()) {
            param("profileServerUrl", profileServerUrl)
        }

        param("agentPushPreset", agentPushPreset)

        param("terminate-idle-time", terminateAfterIdleTime.toString())
        param("terminate-after-build", terminateAfterBuild.toString())
        if (terminateMinutesBeforeNextHour != 0) {
            param("next-hour", terminateMinutesBeforeNextHour.toString())
        }
        if (terminateAfterTotalWorkTime != 0) {
            param("total-work-time", terminateAfterTotalWorkTime.toString())
        }
    }

    /**
     * Allows to specify cloud images for this profile
     * @param init function to initialize images
     */
    fun images(init: CloudImages.() -> Unit) {
        images.init()
    }
}