package com.theendercore.endnuhuh

import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import net.minecraft.resources.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object EndNuhUh {
    const val MODID = "endnuhuh"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(EndNuhUh::class.simpleName)

    @JvmField
    var config = ConfigApi.registerAndLoadConfig(::ENUConfig)

    fun init() {
        log.info("End?? NUH UHH!!!!!!!")
    }

    fun id(path: String): Identifier = Identifier.fromNamespaceAndPath(MODID, path)

}
