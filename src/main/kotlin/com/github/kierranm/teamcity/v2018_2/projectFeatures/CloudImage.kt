package com.github.kierranm.teamcity.v2018_2.projectFeatures

import jetbrains.buildServer.configs.kotlin.v2018_2.ProjectFeature
import jetbrains.buildServer.configs.kotlin.v2018_2.TeamCityDsl

@TeamCityDsl
open class CloudImage(var profile: CloudProfile) : ProjectFeature() {
    /**
     * The ID of an agent pool that this agent belongs to
     * Defaults to the Default pool
     */
    var agentPoolId: Int = 0
    constructor(profile: CloudProfile, init: CloudImage.() -> Unit) : this(profile) {
        type = "CloudImage"
        init()
        param("agent_pool_id", agentPoolId.toString())
        param("profileId", profile.id!!)
    }
}