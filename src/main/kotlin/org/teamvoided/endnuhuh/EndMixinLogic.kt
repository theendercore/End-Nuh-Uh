package org.teamvoided.endnuhuh

import net.minecraft.item.ItemUsageContext
import net.minecraft.particle.DustParticleEffect
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.Vec3d

object EndMixinLogic {
    @JvmStatic
    fun tryDisablePortal(c: ItemUsageContext): Boolean {
        if (c.world.isClient) return true
        val world = c.world as ServerWorld
        val pos = c.blockPos
        val player = c.player ?: return true
        if (player.isCreative) return false

        world.spawnParticles(
            DustParticleEffect(Vec3d.unpackRgb(0x69a395).toVector3f(), .7f),
            pos.x + 0.5,
            pos.y + (14.0 / 16),
            pos.z + 0.5,
            5,
            0.1,
            0.1,
            0.1,
            0.01
        )
        world.playSound(null, pos, SoundEvents.ENTITY_ENDER_EYE_DEATH, SoundCategory.BLOCKS, 0.8f, 0.1f)
        return true
    }
}