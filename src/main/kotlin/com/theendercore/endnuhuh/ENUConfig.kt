package com.theendercore.endnuhuh

import com.theendercore.endnuhuh.EndNuhUh.MODID
import com.theendercore.endnuhuh.EndNuhUh.id
import me.fzzyhmstrs.fzzy_config.annotations.Comment
import me.fzzyhmstrs.fzzy_config.config.Config
import net.minecraft.commands.Commands
import net.minecraft.world.entity.player.Player

class ENUConfig : Config(id(MODID)) {

    var allowEndPortalOpening = PermissionState.ALLOW_CREATIVE

    @Comment("Allows removing eyes from portal frame blocks when holding sneak")
    var allowEyeRemoval = PermissionState.ALLOW

    @Comment("When removing an eye from an open portal it is closed")
    var allowPortalClosing = PermissionState.ALLOW

    @Comment("Prevents the portal opening noise and particles if a portal is already open. Should be only disabled for compatibility reasons")
    var preventPortalReopening = true

    enum class PermissionState {
        ALLOW, ALLOW_CREATIVE, ALLOW_ADMIN, DENY;

        fun hasPermissions(player: Player): Boolean {
            return when (this) {
                ALLOW -> true
                ALLOW_CREATIVE -> player.isCreative
                ALLOW_ADMIN -> Commands.LEVEL_ADMINS.check(player.permissions())
                DENY -> false
            }
        }
    }

}
