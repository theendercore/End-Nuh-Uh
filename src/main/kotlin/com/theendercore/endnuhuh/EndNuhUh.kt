package com.theendercore.endnuhuh

import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("unused")
object EndNuhUh {
    const val MODID = "endnuhuh"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(EndNuhUh::class.simpleName)

    fun init() {
        log.info("End?? NUH UHH!!!!!!!")
    }

    fun id(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(MODID, path)
}
