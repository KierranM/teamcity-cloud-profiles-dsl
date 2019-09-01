package com.github.kierranm.teamcity.v2018_2.projectFeatures

import jetbrains.buildServer.configs.kotlin.v2018_2.ProjectFeatures
import jetbrains.buildServer.configs.kotlin.v2018_2.TeamCityDsl

@TeamCityDsl
fun CloudImages.ami(init: MultiSubnetEc2CloudImage.() -> Unit): MultiSubnetEc2CloudImage {
    val result = MultiSubnetEc2CloudImage(profile, init)
    for (image in result.toEc2CloudImages()) {
        items.add(image)
    }
    return result
}

@TeamCityDsl
fun ProjectFeatures.ec2CloudProfile(init: Ec2CloudProfile.() -> Unit): Ec2CloudProfile {
    val result = Ec2CloudProfile(init)
    // Add the cloud profile
    items.add(result)
    // Add each of the images
    for (image in result.images.items) {
        items.add(image)
    }
    return result
}