package org.teamvoided.endnuhuh

import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("unused")
object EndNuhUh {
    const val MODID = "endnuhuh"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(EndNuhUh::class.simpleName)

    fun init() {
        log.info("Hello from Common")
    }

    fun id(path: String) = Identifier.of(MODID, path)
}
