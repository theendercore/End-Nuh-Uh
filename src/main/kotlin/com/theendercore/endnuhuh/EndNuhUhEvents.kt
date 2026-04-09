package com.theendercore.endnuhuh

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

object EndNuhUhEvents {

    fun interface PortalCheckCallback {
        fun interact(pos: BlockPos, state: BlockState, world: Level, player: Player): Boolean
    }

    /** Event fired before eye insertion code.
     *
     *  Return true if you want to disable EndNuhUh behavior.
     *  For example, Allow some player with some special condition to open portals, or only prevent some players from opening portals
     * */
    val PRE_INSERT: Event<PortalCheckCallback> =
        EventFactory.createArrayBacked(PortalCheckCallback::class.java) { listeners ->
            PortalCheckCallback { pos, state, world, player ->
                for (callback in listeners) {
                    if (callback.interact(pos, state, world, player)) return@PortalCheckCallback true
                }
                false
            }
        }

    /** Event fired before eye removal code.
     *
     *  Return true if you want to disable EndNuhUh behavior.
     *  For example, if you want to prevent specific players from removing eyes.
     * */
    val PRE_REMOVE: Event<PortalCheckCallback> =
        EventFactory.createArrayBacked(PortalCheckCallback::class.java) { listeners ->
            PortalCheckCallback { pos, state, world, player ->
                for (callback in listeners) {
                    if (callback.interact(pos, state, world, player)) return@PortalCheckCallback true
                }
                false
            }
        }

}