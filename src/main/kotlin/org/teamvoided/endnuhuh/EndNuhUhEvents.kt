package org.teamvoided.endnuhuh

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

object EndNuhUhEvents {
    fun interface PreInsertCallback {
        fun interact(pos: BlockPos, state: BlockState, world: Level, player: Player): Boolean
    }

    /** Event fired before eye insertion code.
     *
     *  Return true if you want to disable EndNuhUh behaviour
     * */
    val PRE_INSERT: Event<PreInsertCallback> =
        EventFactory.createArrayBacked(PreInsertCallback::class.java) { listeners ->
            PreInsertCallback { pos, state, world, player ->
                for (callback in listeners) {
                    if (callback.interact(pos, state, world, player)) return@PreInsertCallback true
                }
                false
            }
        }

    /** Event fired before eye removal code.
     *
     *  Return true if you want to disable EndNuhUh behaviour
     * */
    val PRE_REMOVE: Event<PreInsertCallback> =
        EventFactory.createArrayBacked(PreInsertCallback::class.java) { listeners ->
            PreInsertCallback { pos, state, world, player ->
                for (callback in listeners) {
                    if (callback.interact(pos, state, world, player)) return@PreInsertCallback true
                }
                false
            }
        }

}