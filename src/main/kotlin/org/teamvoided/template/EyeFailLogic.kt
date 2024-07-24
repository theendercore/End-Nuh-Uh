package org.teamvoided.template

import net.minecraft.item.ItemUsageContext
import net.minecraft.particle.ParticleTypes
import net.minecraft.server.world.ServerWorld

object EyeFailLogic {
    @JvmStatic
    fun disablePortal(c: ItemUsageContext) {
        val world = c.world as ServerWorld
        val pos = c.blockPos

        world.spawnParticles(
            ParticleTypes.TRIAL_SPAWNER_DETECTION_OMINOUS,
            pos.x + 0.5,
            pos.y + 1.0,
            pos.z + 0.5,
            10,
            0.0,
            0.1,
            0.0,
            0.1
        )
    }
}