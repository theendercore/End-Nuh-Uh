package com.theendercore.endnuhuh

import net.minecraft.core.BlockPos
import net.minecraft.core.particles.DustParticleOptions
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EndPortalFrameBlock
import net.minecraft.world.level.block.state.BlockState
import kotlin.jvm.optionals.getOrNull

object EndLogic {
    @JvmStatic
    fun tryDisablePortal(c: UseOnContext): Boolean {
        if (c.level !is ServerLevel) return true
        val level = c.level as ServerLevel
        val pos = c.clickedPos
        val state = level.getBlockState(pos)
        val player = c.player ?: return false

        if (EndNuhUhEvents.PRE_INSERT.invoker().interact(pos, state, level, player)) return false

        if (player.isCreative) return false

        level.setBlock(pos, state.setValue(EndPortalFrameBlock.HAS_EYE, true), Block.UPDATE_NONE)
        val testVal = EndPortalFrameBlock.getOrCreatePortalShape().find(level, pos)
        if (testVal != null) {
            level.setBlock(pos, state.setValue(EndPortalFrameBlock.HAS_EYE, false), Block.UPDATE_NONE)
            level.sendParticles(
                DustParticleOptions(0x69a395, .7f),
                pos.x + 0.5,
                pos.y + (14.0 / 16),
                pos.z + 0.5,
                5,
                0.1, 0.1, 0.1,
                0.01
            )
            level.playSound(null, pos, SoundEvents.ENDER_EYE_DEATH, SoundSource.BLOCKS, 0.8f, 0.1f)
            return true
        }
        return false
    }

    @JvmStatic
    fun removeEye(state: BlockState, level: Level, pos: BlockPos, player: Player): InteractionResult? {
        if (EndNuhUhEvents.PRE_REMOVE.invoker().interact(pos, state, level, player)) return null

        if (state.getOptionalValue(EndPortalFrameBlock.HAS_EYE).getOrNull() == true && player.mainHandItem.isEmpty
            && player.isCrouching
        ) {
            level.playSound(null, pos, SoundEvents.ENDER_EYE_LAUNCH, SoundSource.BLOCKS, 0.5f, 3.2f)
            val result = EndPortalFrameBlock.getOrCreatePortalShape().find(level, pos)
            if (result != null) {
                val movingPos = result.frontTopLeft.offset(-3, 0, -3)
                for (i in 0..2) {
                    for (j in 0..2) {
                        val pos2 = movingPos.offset(i, 0, j)
                        if (level.getBlockState(pos2).`is`(Blocks.END_PORTAL)) {
                            level.setBlock(pos2, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL)
                        }
                    }
                }
                level.playSound(null, pos, SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS, 1.0f, 10.0f)
            }

            level.setBlock(pos, state.setValue(EndPortalFrameBlock.HAS_EYE, false), Block.UPDATE_ALL)

            if (!player.isCreative) {
                val item = ItemEntity(
                    level,
                    pos.x + 0.5,
                    pos.y + 0.98,
                    pos.z + 0.5,
                    Items.ENDER_EYE.defaultInstance
                )
                item.setDeltaMovement(0.0, 0.0, 0.0)
                level.addFreshEntity(item)
            } else if (!player.getInventory().contains(Items.ENDER_EYE.defaultInstance)) {
                player.getInventory().add(Items.ENDER_EYE.defaultInstance)
            }
            return InteractionResult.SUCCESS
        }
        return null
    }
}