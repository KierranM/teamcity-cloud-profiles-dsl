package io.github.kierranm.teamcity.v2018_2.projectFeatures

import jetbrains.buildServer.configs.kotlin.v2018_2.TeamCityDsl

@TeamCityDsl
open class MultiSubnetEc2CloudImage(val profile: CloudProfile) {
    /**
     * The AMI ID of this agent
     */
    lateinit var ami: String
    /**
     * The EC2 KeyPair name
     */
    lateinit var ec2KeyPair: String
    /**
     * A list of subnets which agents will be created in
     */
    lateinit var subnetIds: List<String>
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

    /**
     * The ID of an agent pool that this agent belongs to
     * Defaults to the Default pool
     */
    var agentPoolId: Int = 0

    constructor(profile: CloudProfile, init: MultiSubnetEc2CloudImage.() -> Unit) : this(profile) {
        init()
    }

    fun toEc2CloudImages() : List<Ec2CloudImage> {
        var images = mutableListOf<Ec2CloudImage>()
        for (subnet in subnetIds) {
            val image = Ec2CloudImage(profile) {
                ami = ami
                ec2KeyPair = ec2KeyPair
                subnetId = subnet
                instanceType = instanceType
                userData = userData
                iamInstanceProfile = iamInstanceProfile
                instanceTags = instanceTags
                agentNamePrefix = agentNamePrefix
                useSpotInstance = useSpotInstance
                spotInstancePrice = spotInstancePrice
                securityGroupIds = securityGroupIds
                ebsOptimised = ebsOptimised
            }
            images.add(image)
        }
        return images.toList()
    }
}