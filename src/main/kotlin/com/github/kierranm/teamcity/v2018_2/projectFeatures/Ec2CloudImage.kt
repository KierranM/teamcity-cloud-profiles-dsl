package com.github.kierranm.teamcity.v2018_2.projectFeatures

import jetbrains.buildServer.configs.kotlin.v2018_2.TeamCityDsl

@TeamCityDsl
open class Ec2CloudImage(profile: CloudProfile) : CloudImage(profile) {
    /**
     * The AMI ID of this agent
     */
    lateinit var ami: String
    /**
     * The EC2 KeyPair name
     */
    lateinit var ec2KeyPair: String
    /**
     * The Subnet ID the agent will be created in
     */
    lateinit var subnetId: String
    /**
     * The EC2 Instance Type that will be created
     */
    lateinit var instanceType: String
    /**
     * The EC2 UserData that will be executed when the agent starts
     */
    var userData: String = ""
    /**
     * The Instance profile of the agent
     */
    var iamInstanceProfile: String = ""
    /**
     * Resource Tags that will be applied to the instance
     */
    var instanceTags: Map<String, String> = mapOf()
    /**
     * An optional prefix for the agent. Defaults to EC2-
     */
    var agentNamePrefix: String = ""
    /**
     * Whether to create a spot instance request
     */
    var useSpotInstance: Boolean = false
    /**
     * The maximum bid value for a spot instance
     */
    var spotInstancePrice: Double = 0.0
    /**
     * A list of security group ids to apply to the instance
     */
    var securityGroupIds: List<String> = listOf()
    /**
     * Whether the instance will be EBS optimised or not
     */
    var ebsOptimised: Boolean = false

    constructor(profile: CloudProfile, init: Ec2CloudImage.() -> Unit) : this(profile) {
        init()
        param("amazon-id", ami)
        param("source-id", agentNamePrefix)
        param("image-name-prefix", agentNamePrefix)
        param("use-spot-instances", useSpotInstance.toString())
        if (useSpotInstance) {
            param("spot-instance-price", spotInstancePrice.toString())
        }

        param("user-tags", instanceTags.map { (key, value) -> "$key=$value" }.joinToString(","))
        param("subnet-id", subnetId)
        param("ebs-optimized", ebsOptimised.toString())
        param("iam-instance-profile", iamInstanceProfile)
        param("instance-type", instanceType)
        param("user-script", userData)
        param("key-pair-name", ec2KeyPair)
        param("security-group-ids", securityGroupIds.joinToString(","))
    }
}