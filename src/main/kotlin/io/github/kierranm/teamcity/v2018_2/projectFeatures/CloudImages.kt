package io.github.kierranm.teamcity.v2018_2.projectFeatures

import jetbrains.buildServer.configs.kotlin.v2018_2.ErrorConsumer
import jetbrains.buildServer.configs.kotlin.v2018_2.TeamCityDsl
import jetbrains.buildServer.configs.kotlin.v2018_2.Validatable

/**
 * Collection of [CloudImage] in a [CloudProfile]
 */
@TeamCityDsl
class CloudImages(var profile: CloudProfile): Validatable {
    val items = arrayListOf<CloudImage>()

    /**
     * Adds the specified cloud image
     * @param image cloud image to add
     */
    fun image(image: CloudImage) {
        items.add(image)
    }

    /**
     * Adds a CloudImage initialized with specified init block
     * @param init block to initialize the cloud image
     * @return added cloud image
     */
    fun image(init: CloudImage.() -> Unit): CloudImage {
        val result = CloudImage(profile, init)
        items.add(result)
        return result
    }

    override fun validate(consumer: ErrorConsumer) {
        items.forEachIndexed { idx, image ->
            consumer.nested("project feature [${idx + 1}/${items.size}]") {
                image.validate(consumer)
            }
        }
    }
}