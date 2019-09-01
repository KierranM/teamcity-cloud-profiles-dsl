package com.github.kierranm.teamcity.v2018_2.projectFeatures

import jetbrains.buildServer.configs.kotlin.v2018_2.TeamCityDsl

@TeamCityDsl
open class Ec2CloudProfile() : CloudProfile() {
    /**
     * Whether the TeamCity servers IAM role will be used to create instances
     * Defaults to true. Must be false if specifying Access Keys
     */
    var useInstanceIAMRole: Boolean = true
    var accessId: String = ""
    var secretKey: String = ""
    lateinit var region: String

    /**
     * Creates a cloud profile feature and initializes it with the specified init block
     * @param init initialization block
     */
    constructor(init: Ec2CloudProfile.() -> Unit) : this() {
        cloudCode = "amazon"
        init()
        param("region", region)
        param("use-instance-iam-role", useInstanceIAMRole.toString())

        if (!useInstanceIAMRole) {
            param("secure:access-id", accessId)
            param("secure:secret-key", secretKey)

        }
        // param("spot-fleet-config", "")
    }
}