package org.teamvoided.endnuhuh

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.EndPortalFrameBlock
import net.minecraft.entity.ItemEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemUsageContext
import net.minecraft.item.Items
import net.minecraft.particle.DustParticleEffect
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import kotlin.jvm.optionals.getOrNull

object EndLogic {
    @JvmStatic
    fun tryDisablePortal(c: ItemUsageContext): Boolean {
        if (c.world !is ServerWorld) return true
        val world = c.world as ServerWorld
        val pos = c.blockPos
        val state = world.getBlockState(pos)
        val player = c.player ?: return false

        if (EndNuhUhEvents.PRE_INSERT.invoker().interact(pos, state, world, player)) return false

        if (player.isInCreativeMode) return false

        world.setBlockState(pos, state.with(EndPortalFrameBlock.EYE, true), Block.SKIP_UPDATES)
        val testVal = EndPortalFrameBlock.getCompletedFramePattern().searchAround(world, pos)
        if (testVal != null) {
            world.setBlockState(pos, state.with(EndPortalFrameBlock.EYE, false), Block.SKIP_UPDATES)
            world.spawnParticles(
                DustParticleEffect(0x69a395, .7f),
                pos.x + 0.5,
                pos.y + (14.0 / 16),
                pos.z + 0.5,
                5,
                0.1, 0.1, 0.1,
                0.01
            )
            world.method_8396(null, pos, SoundEvents.ENTITY_ENDER_EYE_DEATH, SoundCategory.BLOCKS, 0.8f, 0.1f)
            return true
        }
        return false
    }

    @JvmStatic
    fun removeEye(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity): ActionResult? {
        if (EndNuhUhEvents.PRE_REMOVE.invoker().interact(pos, state, world, player)) return null

        if (state.getOrEmpty(EndPortalFrameBlock.EYE).getOrNull() == true && player.mainHandStack.isEmpty && player.isSneaking) {

            world.method_8396(null, pos, SoundEvents.ENTITY_ENDER_EYE_LAUNCH, SoundCategory.BLOCKS, 0.5f, 3.2f)

            val result = EndPortalFrameBlock.getCompletedFramePattern().searchAround(world, pos)
            if (result != null) {
                val movingPos = result.frontTopLeft.add(-3, 0, -3)
                for (i in 0..2) {
                    for (j in 0..2) {
                        val pos2 = movingPos.add(i, 0, j)
                        if (world.getBlockState(pos2).isOf(Blocks.END_PORTAL)) {
                            world.setBlockState(pos2, Blocks.AIR.defaultState, Block.NOTIFY_LISTENERS)
                        }
                    }
                }
                world.method_8396(null, pos, SoundEvents.BLOCK_END_PORTAL_SPAWN, SoundCategory.BLOCKS, 1.0f, 10.0f)
            }

            world.setBlockState(pos, state.with(EndPortalFrameBlock.EYE, false))

            if (!player.isInCreativeMode) {
                val item = ItemEntity(
                    world,
                    pos.x + 0.5,
                    pos.y + 0.98,
                    pos.z + 0.5,
                    Items.ENDER_EYE.defaultStack
                )
                item.setVelocity(0.0, 0.0, 0.0)
                world.spawnEntity(item)
            } else if (!player.getInventory().contains(Items.ENDER_EYE.defaultStack)) {
                player.getInventory().insertStack(Items.ENDER_EYE.defaultStack)
            }
            return ActionResult.SUCCESS
        }
        return null
    }
}